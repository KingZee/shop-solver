package scheduler;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Problem {      //Class representing an instance of the optimization problem

    public int jobCount = 0;
    public int machineCount = 0;

    private int timeMatrix[][];     //Matrix of processing times
    private int machineMatrix[][];  //Matrix of precedence constraints (damn you jobshop)
    private ShopType type;          //Type of the optimization problem

    public Problem() {
        timeMatrix = new int[0][0];
        machineMatrix = new int[0][0];
        type = ShopType.FLOW;
    }

    public Problem(int jobs, int machines) { //Empty constructor
        jobCount = jobs;
        machineCount = machines;
        timeMatrix = new int[jobs][machines];
        machineMatrix = new int[jobs][machines];
        type = ShopType.FLOW;
    }

    public Problem(int jobs, int machines, ShopType type, Point timeInterval, double zeroChance) {
        int[][] outTime = new int[jobs][machines];
        int[][] outMx = new int[jobs][machines];
        for (int j = 0; j < jobs; j++) {
            for (int m = 0; m < machines; m++) {
                double time = Math.random();
                time = time < zeroChance ? 0 : (time - zeroChance) * (timeInterval.y - timeInterval.x) / (1 - zeroChance) + timeInterval.x;
                outTime[j][m] = (int) Math.round(time);
            }
            if (type == ShopType.JOB) {  //Only Job Shop has need for precedence constraints
                outMx[j] = shuffleArray(IntStream.rangeClosed(0, machines - 1).toArray());
            }
        }

        this.jobCount = jobs;
        this.machineCount = machines;
        this.timeMatrix = outTime;
        this.type = type;
        this.machineMatrix = type == ShopType.JOB ? outMx : null;
    }

    public Problem(int[][] time, int[][] machine, ShopType type) {
        jobCount = time.length;
        machineCount = time[0].length;
        timeMatrix = time;
        machineMatrix = machine;
        this.type = type;
    }

    public int[][] getTimeMatrix() {
        return timeMatrix;
    }

    public int[][] getMachineMatrix() {
        return machineMatrix;
    }

    public ShopType getType() {
        return type;
    }

    public void changeType(ShopType type) {
        this.type = type;
    }

    public void updateType(ShopType type) {
        this.type = type;
        if (type != ShopType.JOB) {
            machineMatrix = null;
            return;
        }
        for (int j = 0; j < jobCount; j++)
            machineMatrix[j] = shuffleArray(IntStream.rangeClosed(0, machineCount - 1).toArray());
    }

    public void updateTime(int rowIndex, int colIndex, int value) {
        timeMatrix[rowIndex][colIndex] = value;
    }

    public void updateMachine(int rowIndex, int colIndex, int value) {
        if (value > machineCount) throw new ArrayIndexOutOfBoundsException();
        machineMatrix[rowIndex][colIndex] = value;
    }

    // Implementing Fisher–Yates shuffle
    public static int[] shuffleArray(int[] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }


}
