package scheduler.solvers;

import com.google.common.collect.Lists;
import javafx.concurrent.Task;
import scheduler.Problem;
import scheduler.Schedule;
import scheduler.ShopType;
import scheduler.Solver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ExactSolver extends Solver {

    public ExactSolver(Problem prb) {
        super(prb);
    }

    @Override
    protected List<Schedule> solveMakespan(Task currentTask) {

        if (this.getProblem().machineCount == 0) {
            System.err.println("No matrix set");
            return new ArrayList<>();
        }

        int[][] matrix = getProblem().getTimeMatrix();

        final List<Schedule> schedules = new ArrayList<>(); //To populate with final solution

        if (getProblem().getType() == ShopType.FLOW) {
            Schedule[] baseMachines = new Schedule[getProblem().machineCount];

            //Generate schedule per job
            for (int i = 0; i < baseMachines.length; i++) {
                baseMachines[i] = new Schedule();
                for (int j = 0; j < matrix.length; j++) {
                    if (matrix[j][i] != 0) {
                        baseMachines[i].put(new Point(j,i), matrix[j][i]);
                    }
                }
            }

            List<List<Schedule>> baseSchedule = new ArrayList<>();
            //Permutations of each job's schedule
            for (Schedule sch : baseMachines) {
                baseSchedule.add(permute(sch, sch.size()));
            }

            //Cartesian array combination of schedules together
            List<List<Schedule>> full = Lists.cartesianProduct(baseSchedule);    //4jobs x 8 machines, 7x3, 5x5

            //Concatenate into final Schedules containing all jobs + machines
            for(List<Schedule> sch : full){
                schedules.add(Schedule.concat(sch));
            }

            //Calculate makespan
            for (Schedule sch : schedules) {
                sch.forEach((index, time) -> sch.put(index, time + getPreviousTime(sch, index.x, index.y)));
            }

        } else if (getProblem().getType() == ShopType.OPEN) {
            Schedule baseSchedule = new Schedule();

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (matrix[i][j] != 0) {
                        baseSchedule.put(new Point(j,i), matrix[i][j]);
                    }
                }
            }

            System.out.println("guaranteed");

            schedules.addAll(permute(baseSchedule,baseSchedule.size()));

            //Calculate makespan
            for (Schedule sch : schedules) {
                //if(currentTask.isCancelled()) System.out.println("Wee woo we cancelled");
                //System.out.println(schedules.indexOf(sch)+"/"+schedules.size());
                sch.forEach((index, time) -> sch.put(index, time + getPreviousTime(sch, index.x, index.y)));
            }

        } else {

        }

        System.out.println(schedules);

        return schedules;

    }

    //Get the worst end time for the previous task
    private int getPreviousTime(Schedule schedule, int jobIndex, int machineIndex){
        Integer precedent = schedule.get(schedule.getPrecedentJob(new Point(jobIndex,machineIndex)));
        Integer previous = schedule.get(schedule.getPreviousJob(new Point(jobIndex,machineIndex)));

        precedent = precedent == null ? 0 : precedent;
        previous = previous == null ? 0 : previous;

        return Math.max(precedent,previous);
    }

}
