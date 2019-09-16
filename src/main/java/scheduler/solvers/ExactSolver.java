package scheduler.solvers;

import com.google.common.collect.Lists;
import scheduler.Problem;
import scheduler.Schedule;
import scheduler.ShopType;
import scheduler.Solver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An exact solver that iterates through each problem's solution space
 */
public class ExactSolver extends Solver {

    public ExactSolver(Problem prb) {
        super(prb);
    }

    @Override
    protected List<Schedule> solveMakespan() {

        if (this.getProblem().machineCount == 0) {
            System.err.println("No matrix set");
            return new ArrayList<>();
        }

        int[][] timeMatrix = getProblem().getTimeMatrix();
        int[][] machineMatrix = getProblem().getMachineMatrix();

        final List<Schedule> schedules = new ArrayList<>(); //To populate with final solution

        if (getProblem().getType() == ShopType.FLOW) {
            Schedule[] baseMachines = new Schedule[getProblem().machineCount];

            //Generate schedule per job, note this is iterating vertically
            for (int i = 0; i < baseMachines.length; i++) {
                baseMachines[i] = new Schedule();
                for (int j = 0; j < timeMatrix.length; j++) {
                    if (timeMatrix[j][i] != 0) {
                        baseMachines[i].put(new Point(j, i), timeMatrix[j][i]);
                    }
                }
            }

            List<List<Schedule>> baseSchedule = new ArrayList<>();
            //Permutations of each job's schedule
            for (Schedule sch : baseMachines) {
                if (sch.size() != 0) baseSchedule.add(permute(sch, sch.size()));
            }

            //Cartesian array combination of schedules together
            List<List<Schedule>> full = Lists.cartesianProduct(baseSchedule);    //4jobs x 8 machines, 7x3, 5x5

            //Concatenate into final Schedules containing all jobs + machines
            for (List<Schedule> sch : full) {
                schedules.add(Schedule.concat(sch));
            }

        } else if (getProblem().getType() == ShopType.OPEN) {
            Schedule[] baseMachines = new Schedule[getProblem().machineCount];

            //Generate schedule per job, note this is iterating vertically
            for (int i = 0; i < baseMachines.length; i++) {
                baseMachines[i] = new Schedule();
                for (int j = 0; j < timeMatrix.length; j++) {
                    if (timeMatrix[j][i] != 0) {
                        baseMachines[i].put(new Point(j, i), timeMatrix[j][i]);
                    }
                }
            }

            List<List<Schedule>> baseSchedule = new ArrayList<>();
            //Permutations of each job's schedule
            for (Schedule sch : baseMachines) {
                if (sch.size() != 0) baseSchedule.add(permute(sch, sch.size()));
            }

            //Cartesian array combination of schedules together
            List<List<Schedule>> full = Lists.cartesianProduct(baseSchedule);    //4jobs x 8 machines, 7x3, 5x5
            List<Schedule> initialPermutation = new ArrayList<>();

            //Concatenate into final Schedules containing all jobs + machines
            for (List<Schedule> sch : full) {
                initialPermutation.add(Schedule.concat(sch));
            }

            //Concatenate into final Schedules containing all jobs + machines
            for (Schedule sch : initialPermutation) {
                List<Schedule> machinePerms = new ArrayList<>();
                machinePerms.add(sch);
                for (int i = 0; i < timeMatrix.length; i++) {
                    final List<Schedule> tempPerms = new ArrayList<>();
                    for (Schedule schedule : machinePerms) {
                        tempPerms.addAll(permuteSubset(schedule, i));
                    }
                    machinePerms = tempPerms;
                }
                schedules.addAll(machinePerms);
            }
        } else if (getProblem().getType() == ShopType.JOB) {
            Schedule[] baseMachines = new Schedule[getProblem().machineCount];

            //Generate schedule per order, note this is iterating vertically
            for (int i = 0; i < baseMachines.length; i++) {
                baseMachines[i] = new Schedule();
                for (int j = 0; j < machineMatrix.length; j++) {
                    if (timeMatrix[j][i] != 0) {
                        baseMachines[i].put(new Point(j, machineMatrix[j][i]), timeMatrix[j][i]);
                    }
                }
            }

            List<List<Schedule>> baseSchedule = new ArrayList<>();
            //Permutations of each job's schedule
            for (Schedule sch : baseMachines) {
                if (sch.size() != 0) baseSchedule.add(permute(sch, sch.size()));
            }

            //Cartesian array combination of schedules together
            List<List<Schedule>> full = Lists.cartesianProduct(baseSchedule);    //4jobs x 8 machines, 7x3, 5x5

            //Concatenate into final Schedules containing all jobs + machines
            for (List<Schedule> sch : full) {
                if (sch.size() != 0) schedules.add(Schedule.concat(sch));
            }

        }

        //Calculate makespan
        for (Schedule sch : schedules) {
            sch.forEach((job, time) -> sch.put(job, time + sch.getPreviousTime(job)));
        }

        return schedules;
    }

}
