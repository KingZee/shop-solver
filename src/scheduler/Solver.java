package scheduler;

import javafx.concurrent.Service;

import java.util.ArrayList;
import java.util.List;

public abstract class Solver extends Service<List<List<MachineMap>>> {

    class JobData {
        private int totalSchedules;
        private int bestTime;
        private int worstTime;
        private double avgTime;
        private List<MachineMap> bestSchedule, worstSchedule;

        JobData(int best, int worst, double avg, List<MachineMap> bestSchedule, List<MachineMap> worstSchedule, int totalSchedules) {
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

        public List<MachineMap> getBestSchedule() {
            return bestSchedule;
        }

        public List<MachineMap> getWorstSchedule() {
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

    public abstract List<List<MachineMap>> solveMakespan(); //Solving for minimum makespan, override in child class
    // A "MachineMap" is a structure that maps job operations for a single machine
    //A list of n "MachineMap"s will constitute a full schedule for n jobs
    // and a list of that structure to store the full set of all possible schedules

    //Heap's algorithm to generate all permutations for a set
    public static List<MachineMap> permute(MachineMap map, int n, List<MachineMap> out) {
        if (n == 1) {
            out.add(new MachineMap(map));
            return out;
        } else {
            for (int i = 0; i < n; i++) {
                permute(map, n - 1, out);
                int j = (n % 2 == 0) ? i : 0;
                int t = map.getIndices().get(n - 1);
                map.getIndices().set(n - 1, map.getIndices().get(j));
                map.getIndices().set(j, t);
            }
            return out;
        }
    }

    public static List<MachineMap> permute(MachineMap map, int n) {
        return permute(map, n, new ArrayList<>());
    }

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
    public JobData parseSchedules(List<List<MachineMap>> schedules) {
        final int totalSchedules = schedules.size();
        int worst = 0, best = Integer.MAX_VALUE;
        double avg = 0;
        List<MachineMap> bestSchedule = new ArrayList<>();
        List<MachineMap> worstSchedule = new ArrayList<>();
        for (List<MachineMap> schedule : schedules) {
            final MachineMap map = schedule.get(schedule.size() - 1);
            int makespan = 0;
            for (int i = 0; i < map.size(); i++) {
                makespan = map.getByIndex(i) > makespan ? map.getByIndex(i) : makespan;
            }
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