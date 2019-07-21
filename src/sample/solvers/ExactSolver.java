package sample.solvers;

import com.google.common.collect.Lists;
import sample.MachineMap;
import sample.Problem;
import sample.ShopType;
import sample.Solver;

import java.util.ArrayList;
import java.util.List;

public class ExactSolver extends Solver {

    public ExactSolver(Problem prb){
        super(prb);
    }

    @Override
    public ArrayList<ArrayList<MachineMap>> solveMakespan(){

        if(this.getProblem().machineCount == 0){
            System.err.println("No matrix set");
            return new ArrayList<>();
        }

        List<List<MachineMap>> full = generateSolutions();
        ArrayList<ArrayList<MachineMap>> schedules = new ArrayList<>(); //deep copy
        for(List<MachineMap> list : full){
            ArrayList<MachineMap> newlist = new ArrayList<>();
            for(MachineMap mac : list){
                newlist.add(new MachineMap(mac));
            }
            schedules.add(newlist);
        }

        if(getProblem().getType() == ShopType.FLOW){    //Flowshop precedence constraints too ez

            for(List<MachineMap> sch : schedules){  //For every possible schedule
                for(int i=0;i<sch.size();i++){      //For each machine of this schedule
                    MachineMap map = sch.get(i);
                    int finalI = i;
                    map.forEach((index, time) -> {   //For each job running on this machine
                        map.put(index, time + getPreviousTime(sch, finalI, index));
                    });
                }
            }

            return schedules;

        } else {    //TODO add remaining job types
            return new ArrayList<>();
        }
    }

    //Generates all possible solutions for "this" problem
    List<List<MachineMap>> generateSolutions(){
        MachineMap[] baseMachines = new MachineMap[getProblem().machineCount];
        int[][] matrix = getProblem().getTimeMatrix();

        for(int i=0;i<baseMachines.length;i++){
            baseMachines[i] = new MachineMap();
            for (int j=0;j<matrix.length;j++){
                if(matrix[j][i] != 0) {
                    baseMachines[i].put(j,matrix[j][i]);
                }
            }
        }

        ArrayList<ArrayList<MachineMap>> baseSchedule = new ArrayList<>();
        //Permutations of pairs
        for(MachineMap sch : baseMachines) {
            baseSchedule.add(permute(sch,sch.size()));
        }
        return Lists.cartesianProduct(baseSchedule);    //4jobs x 8 machines, 7x3, 5x5
    }

    //Get the worst end time for the previous task
    private int getPreviousTime(List<MachineMap> schedule, int machineIndex, int jobIndex){
        Integer previousJob = schedule.get(machineIndex).getPreviousJob(jobIndex);
        int previousJobTime = previousJob == null ? 0 : schedule.get(machineIndex).get(previousJob);
        if(machineIndex==0){
            return previousJobTime;
        } else {
            Integer precedentJobTime = MachineMap.getPrecedentJob(schedule,machineIndex,jobIndex);
            if(precedentJobTime == null) return previousJobTime;
                else return Math.max(precedentJobTime,previousJobTime);
            }
        }

}
