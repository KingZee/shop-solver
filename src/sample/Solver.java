package sample;

import java.util.ArrayList;

public abstract class Solver {

    Problem problem;        //Each solver can be instantiated with a problem or/and changed later

    public Solver(){
        problem = new Problem();
    }

    public Solver(Problem prb){
        problem = prb;
    }

    final public Problem getProblem() {
        return problem;
    }

    final public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public abstract ArrayList<ArrayList<MachineMap>> solveMakespan(); //Solving for minimum makespan, override in child class
                // A "MachineMap" is a structure that maps job operations for a single machine
                //A list of n "MachineMap"s will constitute a full schedule for n jobs
                // and a list of that structure to store the full set of all possible schedules

    //Heap's algorithm to generate all permutations for a set
    public static ArrayList<MachineMap> permute(MachineMap map, int n, ArrayList<MachineMap> out) {
        if(n == 1) {
            out.add(new MachineMap(map));
            //System.out.println(out);
            //out.add(Arrays.copyOf(list,list.length));
            return out;
        } else {
            for(int i=0; i<n; i++) {
                permute(map,n-1, out);
                int j = ( n % 2 == 0 ) ? i : 0;
                int t = map.getIndices().get(n-1);
                map.getIndices().set(n-1,map.getIndices().get(j));
                map.getIndices().set(j,t);
            }
            return out;
        }
    }

    public static ArrayList<MachineMap> permute(MachineMap map, int n){
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

    public static ArrayList<ArrayList<int[]>> combine(ArrayList<int[]>... sets) {
        int combinations = 1;
        ArrayList<ArrayList<int[]>> out = new ArrayList<>();
        for(int i = 0; i < sets.length; combinations *= sets[i].size(), i++);
        for(int i = 0; i < combinations; i++) {
            int j = 1;
            ArrayList<int[]> line = new ArrayList<>();
            for(ArrayList set : sets) {
                line.add((int[])set.get((i/j)%set.size()));
                j *= set.size();
            }
            out.add(line);
        }
        return out;
    }

    public static int[] convertIntegers(ArrayList<Integer> integers) {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++) {
            ret[i] = integers.get(i);
        }
        return ret;
    }

}