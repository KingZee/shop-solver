package scheduler;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Problem class contains all information about a scheduling problem instance
 */
public class Problem {

    public int jobCount = 0;
    public int machineCount = 0;

    /**
     * The matrix of processing times
     */
    private int[][] timeMatrix;
    /**
     * The matrix of precedence constraints (will only be automatically populated for job-shop)
     */
    private int[][] machineMatrix;
    /**
     * Type of optimization problem
     *
     * @see ShopType
     */
    private ShopType type;

    public Problem() {
        timeMatrix = new int[0][0];
        machineMatrix = new int[0][0];
        type = ShopType.FLOW;
    }

    /**
     * Empty constructor with a specific size,
     * to generate a full problem, use the other constructor
     *
     * @param jobs     size of jobs
     * @param machines size of machines
     * @see #Problem(int, int, ShopType, Point, double)
     */
    public Problem(int jobs, int machines) {
        jobCount = jobs;
        machineCount = machines;
        timeMatrix = new int[jobs][machines];
        machineMatrix = new int[jobs][machines];
        type = ShopType.FLOW;
    }

    /**
     * Constructor to generate a full scheduling problem
     *
     * @param jobs         size of jobs
     * @param machines     size of machines
     * @param type         Job type
     * @param timeInterval Interval of processing times
     * @param zeroChance   Chance of an operation to be empty
     * @see ShopType
     */

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

    public int[][] getTimeMatrix() {
        return timeMatrix;
    }

    public int[][] getMachineMatrix() {
        return machineMatrix;
    }

    public ShopType getType() {
        return type;
    }

    public void setType(ShopType type) {
        this.type = type;
    }

    /**
     * Update processing time of a specific operation
     *
     * @param rowIndex Job Index
     * @param colIndex Machine Index
     * @param value    New Processing time value
     */
    public void updateTime(int rowIndex, int colIndex, int value) {
        timeMatrix[rowIndex][colIndex] = value;
    }

    /**
     * Update machine order matrix
     *
     * @param rowIndex Job Index
     * @param colIndex Machine Index
     * @param value    Order value
     */

    public void updateMachine(int rowIndex, int colIndex, int value) {
        if (value > machineCount) throw new ArrayIndexOutOfBoundsException();
        machineMatrix[rowIndex][colIndex] = value;
    }

    // Implementing Fisher–Yates shuffle
    private static int[] shuffleArray(int[] ar) {
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
