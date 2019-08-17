package scheduler.solvers;

import scheduler.Problem;
import scheduler.Schedule;
import scheduler.ShopType;
import scheduler.Solver;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuickSolver extends Solver {

    public QuickSolver(Problem prb) {
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
            Schedule dummySchedule = new Schedule();
            for(int i =0; i < timeMatrix.length; i++) dummySchedule.put(new Point(i,0),0);

            List<Schedule> scheduleBase = permute(dummySchedule,dummySchedule.size());

            for(Schedule permutation : scheduleBase){
                Schedule out = new Schedule();
                for(int j =0 ; j < getProblem().machineCount; j++){
                    for(Point job : permutation.getIndices()){
                        int processingTime = timeMatrix[job.x][j];
                        if(processingTime != 0){
                            out.put(new Point(job.x,j),processingTime);
                        }
                    }
                }
                schedules.add(out);
            }

            //Calculate makespan
            for (Schedule sch : schedules) {
                sch.forEach((job, time) -> sch.put(job, time + sch.getPreviousTime(job)));
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

            List<Schedule> baseSchedules = new ArrayList<>();
            //Permutations of each job's schedule
            for (Schedule sch : baseMachines) {
                sch.getIndices().sort(Comparator.comparingInt(sch::get));
                if(sch.size() != 0) baseSchedules.add(sch);
            }

            Schedule initialSchedule = Schedule.concat(baseSchedules);
            Schedule bestPerm = initialSchedule;

            for (int i = 0; i < timeMatrix.length; i++) {
                List<Schedule> tempPerms = new ArrayList<>(permuteSubset(bestPerm, i));
                if(tempPerms.size() != 0) {
                    List<Schedule> parsedPerms = new ArrayList<>();
                    for (Schedule sch : tempPerms) {
                        Schedule copy = new Schedule(sch);
                        sch.forEach((job, time) -> copy.put(job, time + copy.getPreviousTime(job)));
                        parsedPerms.add(copy);
                    }

                    JobData output = parseSchedules(parsedPerms);
                    bestPerm = tempPerms.get(parsedPerms.indexOf(output.getBestSchedule()));
                    schedules.addAll(parsedPerms);
                }
            }

        } else if (getProblem().getType() == ShopType.JOB) {

            Schedule dummySchedule = new Schedule();
            for(int i =0; i < timeMatrix.length; i++) dummySchedule.put(new Point(i,0),0);

            List<Schedule> scheduleBase = permute(dummySchedule,dummySchedule.size());

            for(Schedule permutation : scheduleBase){
                Schedule out = new Schedule();
                for(int j =0 ; j < getProblem().machineCount; j++){
                    for(Point job : permutation.getIndices()){
                        int processingTime = timeMatrix[job.x][j];
                        if(processingTime != 0){
                            out.put(new Point(job.x,machineMatrix[job.x][j]),processingTime);
                        }
                    }
                }
                schedules.add(out);
            }

            //Calculate makespan
            for (Schedule sch : schedules) {
                sch.forEach((job, time) -> sch.put(job, time + sch.getPreviousTime(job)));
            }

        }

        return schedules;
    }

}
