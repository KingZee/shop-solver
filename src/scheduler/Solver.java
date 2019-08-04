package scheduler;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Solver extends Service<List<Schedule>> {

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

    Problem problem;        //Each solver can be instantiated with a problem or/and changed later

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

    protected abstract List<Schedule> solveMakespan(Task currentTask); //Solving for minimum makespan, override in child class
    // A Schedule is an ordered map of jobs (ex. J1M1 J2M1 J1M2 J2M2...)

    //Heap's algorithm to generate all permutations for a Schedule
    public static List<Schedule> permute(Schedule map, int n, List<Schedule> out) {
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

    public static List<Schedule> permute(Schedule map, int n) {
        return permute(map, n, new ArrayList<>());
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

    //Parse schedules into relevant statistics
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