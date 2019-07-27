package scheduler.solvers;

import com.google.common.collect.Lists;
import scheduler.MachineMap;
import scheduler.Problem;
import scheduler.ShopType;
import scheduler.Solver;

import java.util.ArrayList;
import java.util.List;

public class ExactSolver extends Solver {

    public ExactSolver(Problem prb) {
        super(prb);
    }

    @Override
    protected List<List<MachineMap>> solveMakespan() {

        if (this.getProblem().machineCount == 0) {
            System.err.println("No matrix set");
            return new ArrayList<>();
        }

        List<List<MachineMap>> full = generateSolutions();

        final List<List<MachineMap>> schedules = new ArrayList<>(); //To populate with final solution

        if (getProblem().getType() == ShopType.FLOW) {

            for (List<MachineMap> sch : full) {
                List<MachineMap> newlist = new ArrayList<>();
                for (int i = 0; i < sch.size(); i++) {
                    MachineMap map = new MachineMap(sch.get(i));
                    newlist.add(map);
                    int finalI = i;
                    map.forEach((index, time) -> {
                        map.put(index, time + getPreviousTime(newlist, finalI, index));
                    });
                }
                schedules.add(newlist);
            }

            return schedules;

        } else {    //TODO add remaining job types
            return new ArrayList<>();
        }
    }

    //Generates all possible solutions for "this" problem
    List<List<MachineMap>> generateSolutions() {
        MachineMap[] baseMachines = new MachineMap[getProblem().machineCount];
        int[][] matrix = getProblem().getTimeMatrix();

        for (int i = 0; i < baseMachines.length; i++) {
            baseMachines[i] = new MachineMap();
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i] != 0) {
                    baseMachines[i].put(j, matrix[j][i]);
                }
            }
        }

        List<List<MachineMap>> baseSchedule = new ArrayList<>();
        //Permutations of pairs
        for (MachineMap sch : baseMachines) {
            baseSchedule.add(permute(sch, sch.size()));
        }

        return Lists.cartesianProduct(baseSchedule);    //4jobs x 8 machines, 7x3, 5x5
    }

    //Get the worst end time for the previous task
    private int getPreviousTime(List<MachineMap> schedule, int machineIndex, int jobIndex) {
        Integer previousJob = schedule.get(machineIndex).getPreviousJob(jobIndex);
        int previousJobTime = previousJob == null ? 0 : schedule.get(machineIndex).get(previousJob);
        if (machineIndex == 0) {
            return previousJobTime;
        } else {
            Integer precedentJobTime = MachineMap.getPrecedentJob(schedule, machineIndex, jobIndex);
            if (precedentJobTime == null) return previousJobTime;
            else return Math.max(precedentJobTime, previousJobTime);
        }
    }

}
