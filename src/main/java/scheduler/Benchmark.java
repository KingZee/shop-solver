package scheduler;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Benchmark extends Service<List<Benchmark.SolverData>> {

    class SolverData {
        private Class<? extends Solver> algorithm;
        private List<BenchmarkData> benchmarkData = new ArrayList<>();
        private static final long MEGABYTE = 1024L * 1024L;

        SolverData(Class<? extends Solver> solver) {
            this.algorithm = solver;
        }

        void addDataSet(BenchmarkData data) {
            benchmarkData.add(data);
        }

        Class<? extends Solver> getAlgorithm() {
            return algorithm;
        }

        float getTimeAverage() {
            float sum = 0;
            for (BenchmarkData data : benchmarkData) {
                sum += data.getSolution().getAvgTime();
            }
            return sum / benchmarkData.size();
        }

        long getCpuAverage() {
            long sum = 0;
            for (BenchmarkData data : benchmarkData) {
                sum += data.getCpuUsage();
            }
            return sum / benchmarkData.size();
        }

        long getMemoryAverage() {
            long sum = 0;
            for (BenchmarkData data : benchmarkData) {
                sum += data.getMemoryUsage();
            }
            return (sum / benchmarkData.size()) / MEGABYTE;
        }

        int getBestTime() {
            return Collections.min(benchmarkData, Comparator.comparingInt(o -> o.getSolution().getBestTime())).getSolution().getBestTime();
        }

        int getWorstTime() {
            return Collections.max(benchmarkData, Comparator.comparingInt(o -> o.getSolution().getWorstTime())).getSolution().getWorstTime();
        }

        long getCpuMax() {
            return Collections.max(benchmarkData, Comparator.comparingLong(BenchmarkData::getCpuUsage)).getCpuUsage();
        }

        long getCpuMin() {
            return Collections.min(benchmarkData, Comparator.comparingLong(BenchmarkData::getCpuUsage)).getCpuUsage();
        }

        long getMemoryMax() {
            return Collections.max(benchmarkData, Comparator.comparingLong(BenchmarkData::getMemoryUsage)).getMemoryUsage() / MEGABYTE;
        }

        long getMemoryMin() {
            return Collections.min(benchmarkData, Comparator.comparingLong(BenchmarkData::getMemoryUsage)).getMemoryUsage() / MEGABYTE;
        }

    }

    private List<SolverData> benchmarkResults = new ArrayList<>();

    private static ThreadMXBean threadManager = ManagementFactory.getThreadMXBean();
    private static MemoryMXBean memoryManager = ManagementFactory.getMemoryMXBean();
    private List<Class<? extends Solver>> solverList;
    private int warmupRuns = 2;
    private int benchmarkRuns;
    private Point maxMatrixSize;

    public Benchmark(int runs, int maxJobs, int maxMachines, List<Class<? extends Solver>> solvers) {
        this.benchmarkRuns = runs;
        this.maxMatrixSize = new Point(maxJobs, maxMachines);
        this.solverList = solvers;
    }

    public static boolean isBenchmarkingSupported() {
        if (!threadManager.isThreadCpuTimeSupported())
            return false;

        threadManager.setThreadCpuTimeEnabled(true);
        return threadManager.isThreadCpuTimeEnabled();
    }

    @Override
    protected Task<List<SolverData>> createTask() {
        return new Task<List<SolverData>>() {

            Task activeTask = null;

            @Override
            protected List<SolverData> call() throws Exception {
                for (int i = 0; i < warmupRuns; i++) {
                    for (Class<? extends Solver> solverType : solverList) {
                        ThreadLocalRandom rand = ThreadLocalRandom.current();
                        final int jobs = rand.nextInt(2, maxMatrixSize.x + 1);
                        final int machines = rand.nextInt(2, maxMatrixSize.y + 1);
                        final Point interval = new Point(rand.nextInt(1, 100), rand.nextInt(1, 100));
                        final ShopType type = ShopType.getRandom();
                        final double zeroChance = rand.nextFloat() * 0.12f;
                        Problem problem = new Problem(jobs, machines, type, interval, zeroChance);
                        Solver solver = solverType.getConstructor(Problem.class).newInstance(problem);
                        solver.setProblem(problem);
                        activeTask = benchmarkSolver(solver);
                        Thread taskThread = new Thread(activeTask);
                        taskThread.setDaemon(true);
                        taskThread.start();
                        taskThread.join();
                        if (isCancelled()) return null;
                    }
                }

                for (Class<? extends Solver> solverType : solverList) {
                    SolverData data = new SolverData(solverType);
                    benchmarkResults.add(data);
                }

                for (int i = 0; i < benchmarkRuns; i++) {
                    ThreadLocalRandom rand = ThreadLocalRandom.current();
                    final int jobs = rand.nextInt(2, maxMatrixSize.x + 1);
                    final int machines = rand.nextInt(2, maxMatrixSize.y + 1);
                    final Point interval = new Point(rand.nextInt(1, 100), rand.nextInt(1, 100));
                    final ShopType type = ShopType.getRandom();
                    final double zeroChance = rand.nextFloat() * 0.12f;
                    Problem problem = new Problem(jobs, machines, type, interval, zeroChance);
                    for (int j = 0; j < solverList.size(); j++) {
                        Class<? extends Solver> solverType = solverList.get(j);
                        Solver solver = solverType.getConstructor(Problem.class).newInstance(problem);
                        solver.setProblem(problem);

                        activeTask = benchmarkSolver(solver);
                        Thread taskThread = new Thread(activeTask);
                        taskThread.setDaemon(true);
                        taskThread.start();
                        taskThread.join();

                        if (activeTask.isDone()) benchmarkResults.get(j).addDataSet((BenchmarkData) activeTask.get());
                        else if (activeTask.isCancelled()) return null;
                    }
                }

                return benchmarkResults;
            }

            @Override
            protected void cancelled() {
                super.cancelled();
                activeTask.cancel();
            }
        };
    }

    private Task<BenchmarkData> benchmarkSolver(Solver solver) {
        return new Task<>() {
            private Thread taskThread;
            private BenchmarkData data = new BenchmarkData();
            private long initialMemory = memoryManager.getHeapMemoryUsage().getUsed();

            @Override
            protected BenchmarkData call() throws Exception {
                taskThread = Thread.currentThread();
                Task currentTask = this;
                Timer checker = new Timer();
                checker.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            if (currentTask.isDone()) {
                                cancel();
                                System.gc();
                            }
                            if (currentTask.isCancelled()) taskThread.stop();
                            taskThread.suspend();
                            Thread.sleep(20);
                            taskThread.resume();
                        } catch (Exception ex) {
                            ex.printStackTrace(System.out);
                        }
                    }
                }, 0, 50);
                List<Schedule> result = solver.solveMakespan();
                Solver.JobData parsedResult = Solver.parseSchedules(result);
                long cpuTime = threadManager.getThreadCpuTime(taskThread.getId());
                ThreadInfo threadData = threadManager.getThreadInfo(taskThread.getId());
                long memoryUsed = Math.max(memoryManager.getHeapMemoryUsage().getUsed() - initialMemory, 0);
                data.setDataSet(solver.getProblem(), parsedResult, cpuTime, memoryUsed, threadData);
                return data;
            }
        };
    }
}
