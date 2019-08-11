package scheduler;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class to inherit from when creating a new Solver type
 * It has helper functions to permute, combine, and parse schedules
 * It inherits from Service to be able to solve the problem in the background
 */

public abstract class Solver extends Service<List<Schedule>> {

    /**
     * Local class to represent information about the current schedule
     */
    static class JobData {
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

        public int getBestTime() {
            return bestTime;
        }

        public int getWorstTime() {
            return worstTime;
        }

        public double getAvgTime() {
            return avgTime;
        }

        public int getTotalSchedules() {
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
     * @see Problem
     */
    Problem problem;

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
    public Task<List<Schedule>> createTask() {
        return new Task<List<Schedule>>() {

            @Override
            protected List<Schedule> call() throws Exception {
                List<Schedule> result = solveMakespan(this);
                return result;
            }
        };
    }

    /**
     * This is the main function used to solve problem instances
     * It is abstract and only usable from children
     * It is also Task-aware, through the currentTask parameter
     *
     * @param currentTask the current Task instantiated with this instance.
     *                    Implementations of this function should listen to the isCancelled() event,
     *                    to cancel computation if the task itself is cancelled.
     * @return A List of Schedules, representing the solution space
     * @see Schedule
     */
    protected abstract List<Schedule> solveMakespan(Task currentTask);

    /**
     * Heap's algorithm to generate all permutations of a Schedule
     *
     * @param map         A single Schedule to generate permutations for
     * @param n           Length of the Schedule
     * @param currentTask This function is also task-aware
     * @return List of permuted schedules
     */
    public static List<Schedule> permute(Schedule map, int n, Task currentTask) {
        return permute(map, n, new ArrayList<>(), currentTask);
    }

    private static List<Schedule> permute(Schedule map, int n, List<Schedule> out, Task currentTask) {
        if (n == 1) {
            out.add(new Schedule(map));
            return out;
        } else {
            for (int i = 0; i < n; i++) {
                if (currentTask.isCancelled()) return null;
                permute(map, n - 1, out, currentTask);
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
     * @param map         A single Schedule to generate permutations for
     * @param jobIndex    Index of the job to permute operations for
     * @param currentTask This function is also task-aware
     * @return List of permuted schedules
     */

    public static List<Schedule> permuteSubset(Schedule map, int jobIndex, Task currentTask) {
        List<Point> indices = new ArrayList<>(map.getIndices());
        indices.removeIf(point -> point.x != jobIndex);
        Schedule jobs = new Schedule();
        indices.forEach(point -> jobs.put(point,map.getIndices().indexOf(point)));
        List<Schedule> permutedJobs = permute(jobs,jobs.size(),currentTask);
        List<Schedule> out = new ArrayList<>();
        for(Schedule partialSchedule : permutedJobs){
            Schedule outputSchedule = new Schedule(map);
            for(int i = 0; i<jobs.size(); i++){
                outputSchedule.getIndices().set(jobs.getByIndex(i),partialSchedule.getIndices().get(i));
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
        Schedule bestSchedule = null,
                worstSchedule = null;
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
        return new JobData(best, worst, avg, bestSchedule, worstSchedule, totalSchedules);
    }

}