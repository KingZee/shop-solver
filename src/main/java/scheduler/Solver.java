package scheduler;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Abstract class to inherit from when creating a new Solver type
 * It has helper functions to permute, combine, and parse schedules
 * It inherits from Service to be able to solve the problem in the background
 */

public abstract class Solver extends Service<List<Schedule>> {

    /**
     * Local class to represent information about the current schedule list
     */
    protected static class JobData {
        private int totalSchedules;
        private int bestTime;
        private int worstTime;
        private double avgTime;
        private Schedule bestSchedule, worstSchedule;

        JobData(int best, int worst, double avg, Schedule bestSchedule, Schedule worstSchedule, int totalSchedules) {
            bestTime = best;
            worstTime = worst;
            avgTime = avg;
            this.bestSchedule = bestSchedule;
            this.worstSchedule = worstSchedule;
            this.totalSchedules = totalSchedules;
        }

        int getBestTime() {
            return bestTime;
        }

        int getWorstTime() {
            return worstTime;
        }

        double getAvgTime() {
            return avgTime;
        }

        int getTotalSchedules() {
            return totalSchedules;
        }

        public Schedule getBestSchedule() {
            return bestSchedule;
        }

        public Schedule getWorstSchedule() {
            return worstSchedule;
        }
    }

    /**
     * Each solver instance is always tied to a problem instance
     * This can be set at instantiation or modified later
     *
     * @see Problem
     */
    private Problem problem;

    public Solver() {
        problem = new Problem();
    }

    public Solver(Problem prb) {
        problem = prb;
    }

    final public Problem getProblem() {
        return problem;
    }

    final public void setProblem(Problem problem) {
        this.problem = problem;
    }

    /**
     * This creates the task that encapsulates the solver function
     *
     * @return task that will resolve with the solved problem if succeeded
     */
    @Override
    protected Task<List<Schedule>> createTask() {
        return new Task<List<Schedule>>() {
            @Override
            protected List<Schedule> call() throws Exception {
                Task currentTask = this;
                Thread taskThread = Thread.currentThread();
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
                            System.out.println(ex);
                        }
                    }
                }, 0, 50);
                List<Schedule> result = solveMakespan();
                return result;
            }
        };
    }

    /**
     * This is the main function used to solve problem instances
     * It is abstract and only usable from children
     * This method is forcefully stopped on user cancel, while memory will be freed once that happens
     * this method should not lock or operate on any objects declared outside of its scope
     *
     * @return A List of Schedules, representing the solution space
     * @see Schedule
     */
    protected abstract List<Schedule> solveMakespan();

    /**
     * Heap's algorithm to generate all permutations of a Schedule
     *
     * @param map A single Schedule to generate permutations for
     * @param n   Length of the Schedule
     * @return List of permuted schedules
     */
    public static List<Schedule> permute(Schedule map, int n) {
        return permute(map, n, new ArrayList<>());
    }

    private static List<Schedule> permute(Schedule map, int n, List<Schedule> out) {
        if (n == 1) {
            out.add(new Schedule(map));
            return out;
        } else {
            for (int i = 0; i < n; i++) {
                permute(map, n - 1, out);
                int j = (n % 2 == 0) ? i : 0;
                Point t = map.getIndices().get(n - 1);
                map.getIndices().set(n - 1, map.getIndices().get(j));
                map.getIndices().set(j, t);
            }
            return out;
        }
    }

    /**
     * Permute operations of a specific job within a schedule
     *
     * @param map      A single Schedule to generate permutations for
     * @param jobIndex Index of the job to permute operations for
     * @return List of permuted schedules
     */
    public static List<Schedule> permuteSubset(Schedule map, int jobIndex) {
        List<Point> indices = new ArrayList<>(map.getIndices());
        indices.removeIf(point -> point.x != jobIndex);
        Schedule jobs = new Schedule();
        indices.forEach(point -> jobs.put(point, map.getIndices().indexOf(point)));
        List<Schedule> permutedJobs = permute(jobs, jobs.size());
        List<Schedule> out = new ArrayList<>();
        for (Schedule partialSchedule : permutedJobs) {
            Schedule outputSchedule = new Schedule(map);
            for (int i = 0; i < jobs.size(); i++) {
                outputSchedule.getIndices().set(jobs.getByIndex(i), partialSchedule.getIndices().get(i));
            }
            out.add(outputSchedule);
        }
        return out;
    }

/*    //Generate all permutations for a Schedule (List MachineMap)
    public static List<List<MachineMap>> permute(List<MachineMap> schedule, int n, List<List<MachineMap>> out) {
        if (n == 1) {
            schedule.forEach((machineMap -> machineMap = new MachineMap(machineMap)));
            out.add(new ArrayList<>(schedule));
            return out;
        } else {
            for (int i = 0; i < n; i++) {
                permute(schedule, n - 1, out);
                int j = (n % 2 == 0) ? i : 0;
                MachineMap t = schedule.get(n - 1);
                schedule.set(n - 1, schedule.get(j));
                schedule.set(j, t);
            }
            return out;
        }
    }

    public static List<List<MachineMap>> permute(List<MachineMap> schedule, int n) {
        return permute(schedule, n, new ArrayList<>());
    }
*/
    /*
    public static ArrayList<Point[]> permute(Point[] list, int n, ArrayList<Point[]> out) {
        if(n == 1) {
            out.add(Arrays.copyOf(list,list.length));
            return out;
        } else {
            for(int i=0; i<n; i++) {
                permute(list,n-1, out);
                int j = ( n % 2 == 0 ) ? i : 0;
                Point t = list[n-1];
                list[n-1] = list[j];
                list[j] = t;
            }
            return out;
        }
    }*/

    /**
     * Function to generate cartesian array combinations
     *
     * @param sets Set of arrays to combine
     * @return Cartesian multiplication of arrays
     */
    public static List<List<int[]>> combine(List<int[]>... sets) {
        int combinations = 1;
        List<List<int[]>> out = new ArrayList<>();
        for (int i = 0; i < sets.length; combinations *= sets[i].size(), i++) ;
        for (int i = 0; i < combinations; i++) {
            int j = 1;
            List<int[]> line = new ArrayList<>();
            for (List set : sets) {
                line.add((int[]) set.get((i / j) % set.size()));
                j *= set.size();
            }
            out.add(line);
        }
        return out;
    }

    /**
     * Parses a list of schedules, and returns relevant statistics
     *
     * @param schedules List of schedules (solution space)
     * @return JobData structure
     */
    public static JobData parseSchedules(List<Schedule> schedules) {
        final int totalSchedules = schedules.size();
        int worst = 0, best = Integer.MAX_VALUE;
        double avg = 0;
        Schedule bestSchedule = new Schedule(),
                worstSchedule = new Schedule();
        for (Schedule schedule : schedules) {
            int makespan = schedule.getMaxValue();
            avg += (double) (makespan) / (double) schedules.size();

            if (makespan > worst) {
                worst = makespan;
                worstSchedule = schedule;
            }

            if (makespan < best) {
                best = makespan;
                bestSchedule = schedule;
            }
        }
        return new JobData(best, worst, avg, new Schedule(bestSchedule), new Schedule(worstSchedule), totalSchedules);
    }

}