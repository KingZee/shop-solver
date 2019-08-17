package scheduler;

import com.google.common.collect.Lists;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Warmup(iterations = 1)
@Fork(value = 1)
@BenchmarkMode(Mode.All)
@State(Scope.Benchmark)
public class Bench {


    ArrayList<int[]>[] benchInput;
/*
    public static void main(String[] args) throws Exception {
        //org.openjdk.jmh.Main.main(args);

        ArrayList<int[]>[] scheduler = new ArrayList[6];
        scheduler[0] = new ArrayList<>(Solver.permute(new int[]{1, 2, 3, 4,5},5));
        //scheduler[0] = new ArrayList<>(Arrays.asList(new int[]{1},new int[]{2},new int[]{3},new int[]{4},new int[]{5},new int[]{6},new int[]{7}));
        //scheduler[0] = new ArrayList<>(Solver.permute(new int[]{9,8,5,10,11},5));
        //scheduler[1] = new ArrayList<>(Arrays.asList(new int[]{4},new int[]{5},new int[]{6},new int[]{4}));
        scheduler[1] = new ArrayList<>(Solver.permute(new int[]{1,0,2,3},4));
        scheduler[2] = new ArrayList<>(Solver.permute(new int[]{5,6,7},3));
        //scheduler[2] = new ArrayList<>(Arrays.asList(new int[]{7},new int[]{8},new int[]{9},new int[]{9},new int[]{5}));
        //scheduler[3] = new ArrayList<>(Arrays.asList(new int[]{7},new int[]{8},new int[]{9},new int[]{10}));
        scheduler[3] = new ArrayList<>(Solver.permute(new int[]{5,6,7,9,5},5));
        scheduler[4] = new ArrayList<>(Arrays.asList(new int[]{7},new int[]{8},new int[]{9}));
        scheduler[5] = new ArrayList<>(Arrays.asList(new int[]{7},new int[]{8}));
        //scheduler[4] = new ArrayList<>(Solver.permute(new int[]{1,2,3},3));

        long startTime, endTime, duration = 0;

        startTime = System.nanoTime();

        List<List<int[]>> out = Lists.cartesianProduct(Arrays.asList(scheduler));

        endTime = System.nanoTime();
        duration = endTime-startTime;

        System.out.println("Combine 1 ended after : "+duration);


        startTime = System.nanoTime();

        ArrayList<ArrayList<int[]>> out1 =findCombinations(scheduler);

        endTime = System.nanoTime();
        duration = endTime-startTime;

        System.out.println("Combine 2 ended after : "+duration);


        startTime = System.nanoTime();

        ArrayList<ArrayList<int[]>> out2 = combine(scheduler);

        endTime = System.nanoTime();
        duration = endTime-startTime;

        System.out.println("Combine 3 ended after : "+duration);

        //System.out.println("All lists are equal being : "+(compare(out1,out)&&compare(cartesianProduct,out)));
    }


    @Setup
    public void setup() {
        ArrayList<int[]>[] scheduler = new ArrayList[8];
        //scheduler[0] = new ArrayList<>(Solver.permute(new int[]{1, 2, 3, 4,5,6,7},7));
        //scheduler[0] = new ArrayList<>(Arrays.asList(new int[]{1},new int[]{2},new int[]{3},new int[]{4},new int[]{5},new int[]{6},new int[]{7}));
        scheduler[0] = new ArrayList<>(Solver.permute(new int[]{9,8,5,10,11},5));
        //scheduler[1] = new ArrayList<>(Arrays.asList(new int[]{4},new int[]{5},new int[]{6},new int[]{4}));
        scheduler[1] = new ArrayList<>(Solver.permute(new int[]{1,0,2,3,4},5));
        scheduler[2] = new ArrayList<>(Solver.permute(new int[]{5,6,7},3));
        //scheduler[2] = new ArrayList<>(Arrays.asList(new int[]{7},new int[]{8},new int[]{9},new int[]{9},new int[]{5}));
        //scheduler[3] = new ArrayList<>(Arrays.asList(new int[]{7},new int[]{8},new int[]{9},new int[]{10}));
        //scheduler[4] = new ArrayList<>(Arrays.asList(new int[]{7},new int[]{8},new int[]{9},new int[]{4},new int[]{4}));
        scheduler[3] = new ArrayList<>(Solver.permute(new int[]{5,6,7},3));
        scheduler[4] = new ArrayList<>(Solver.permute(new int[]{1,1,1},3));
        scheduler[5] = new ArrayList<>(Solver.permute(new int[]{1,2},2));
        scheduler[6] = new ArrayList<>(Solver.permute(new int[]{1,2},2));
        scheduler[7] = new ArrayList<>(Solver.permute(new int[]{1,2,3},2));
        benchInput = scheduler;
    }
*/
    //@Benchmark
    public void combine1(){
        List<List<int[]>> out = Lists.cartesianProduct(Arrays.asList(benchInput));
    }

    //@Benchmark
    public void combine2(){
        ArrayList<ArrayList<int[]>> out = findCombinations(benchInput);
    }

    //@Benchmark
    public void combine3(){
        ArrayList<ArrayList<int[]>> out = combine(benchInput);
    }

    public static ArrayList<ArrayList<int[]>> findCombinations(ArrayList<int[]>... sets) {
        int combinations = 1;
        ArrayList<ArrayList<int[]>> out = new ArrayList<>();
        for(int i = 0; i < sets.length; combinations *= sets[i].size(), i++);
        for(int i = 0; i < combinations; i++) {
            int j = 1;
            ArrayList<int[]> line = new ArrayList<>();
            for(List set : sets) {
                line.add((int[])set.get((i/j)%set.size()));
                j *= set.size();
            }
            out.add(line);
        }
        return out;
    }



    public static ArrayList<ArrayList<int[]>> combine(ArrayList<int[]>... args) {
        ArrayList<ArrayList<int[]>> out = new ArrayList();
        out.add(new ArrayList<>());
        for (int i = 0; i < args.length; i++) {
            ArrayList<ArrayList<int[]>> arr = new ArrayList();
            for (ArrayList el : out) {
                for (int j = 0; j < args[i].size(); j++) {
                    ArrayList<int[]> t = new ArrayList<int[]>(el);
                    t.add(args[i].get(j));
                    arr.add(t);
                }
            }
            out = new ArrayList<>(arr);
        }
        return out;
    }

    private static boolean compare( List<?> l1, List<?> l2 ) {
        // make a copy of the list so the original list is not changed, and remove() is supported
        ArrayList<?> cp = new ArrayList<>( l1 );
        for ( Object o : l2 ) {
            if ( !cp.remove( o ) ) {
                return false;
            }
        }
        return cp.isEmpty();
    }

}