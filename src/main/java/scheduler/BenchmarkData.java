package scheduler;

import java.lang.management.ThreadInfo;

/**
 * Class that holds benchmark information for a single problem instance
 * It is public as a benchmark is multiple problem instances for multiple algorithms.
 * @see Benchmark.SolverData
 */
public class BenchmarkData {

    private Problem problem;
    private Solver.JobData solution;
    private Long cpuUsage;
    private Long memoryUsage;
    private ThreadInfo threadData;

    void setDataSet(Problem prb, Solver.JobData sol, Long cpuTime, Long memoryUsed, ThreadInfo threadData) {
        this.problem = prb;
        this.solution = sol;
        this.cpuUsage = cpuTime;
        this.memoryUsage = memoryUsed;
        this.threadData = threadData;
    }

    Problem getProblem() {
        return problem;
    }

    Solver.JobData getSolution() {
        return solution;
    }

    Long getCpuUsage() {
        return cpuUsage;
    }

    Long getMemoryUsage() {
        return memoryUsage;
    }

    ThreadInfo getThreadData() {
        return threadData;
    }

}
