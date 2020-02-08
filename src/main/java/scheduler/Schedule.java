package scheduler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * A schedule is a special permutation schedule of operations
 * It's stored as a HashMap of Points ( x : Job index, y : Machine index)
 * A local list variable is added to store the scheduling
 */
public class Schedule extends HashMap<Point, Integer> {
    private List<Point> indices = new ArrayList<>();

    /**
     * Create a copy of an existing schedule
     *
     * @param m Schedule to copy
     */
    public Schedule(Schedule m) {
        m.forEach(this::put);
        this.setIndices(m.getIndices());
    }

    public Schedule() {
        super();
    }

    /**
     * Iterates map by order of schedule, returning key &amp; value
     */
    @Override
    public void forEach(BiConsumer<? super Point, ? super Integer> action) {
        for (int i = 0; i < indices.size(); i++)
            action.accept(indices.get(i), getByIndex(i));
    }

    /**
     * Puts the specific job at the end of the queue if it doesn't exist, or replace its value
     *
     * @param key   Point to specify (Job-index,Machine-index)
     * @param value Processing time of specified job
     * @return the previous value associated with the key, or null if there was no mapping
     */
    @Override
    public Integer put(Point key, Integer value) {
        if (this.get(key) == null)
            indices.add(key);
        return super.put(key, value);
    }

    public void putAll(Schedule map) {
        map.forEach(this::put);
    }

    /**
     * Concatenates two or more schedule instances into a single one
     * merging them by order from left to right
     *
     * @param list List of schedules to concatenate
     * @return Single concatenated result
     */
    public static Schedule concat(Collection<? extends Schedule> list) {
        Schedule out = new Schedule();
        list.forEach(out::putAll);
        return out;
    }

    /**
     * Replace a key by specifying its index
     *
     * @param i     Index of key to replace
     * @param value Processing time to replace with
     * @return Old value that was replaced
     */
    public Integer putByIndex(Integer i, Integer value) {
        return this.put(indices.get(i), value);
    }

    /**
     * Fetch a job by its index in the schedule
     *
     * @param i Index of job to get
     * @return Point representation of the job
     */
    public Integer getByIndex(Integer i) {
        return this.get(indices.get(i));
    }

    /**
     * Returns the previous operation on the same machine
     *
     * @param job A Job represented as Point(n,m)
     * @return The job being executed before this one, job Point(n?,m).
     * If it is not found, it tries to find the closest job, or returns null.
     */
    public Point getPreviousJob(Point job) {
        int currentIndex = indices.indexOf(job);
        Point previousJob = null;
        while (currentIndex > 0) {
            currentIndex--;
            if (indices.get(currentIndex).y == job.y) {
                previousJob = indices.get(currentIndex);
                break;
            }
        }
        return previousJob;
    }

    /**
     * Returns the previous operation of the same job
     *
     * @param job A Job represented as Point(n,m)
     * @return The previous job Point(n,m?). If it is not found, it tries to find the closest job, or null.
     */
    public Point getPrecedentJob(Point job) {
        int currentIndex = indices.indexOf(job);
        Point precedentJob = null;
        while (currentIndex > 0) {
            currentIndex--;
            if (indices.get(currentIndex).x == job.x) {
                precedentJob = indices.get(currentIndex);
                break;
            }
        }
        return precedentJob;
    }

    /**
     * Returns the processing time from a job's precedent/previous blocking task
     * Useful for computing makespan
     *
     * @param job Job (jobIndex, machineIndex)
     * @return Processing time of preceding blocking task
     */
    public int getPreviousTime(Point job) {
        Integer precedent = this.get(this.getPrecedentJob(job));
        Integer previous = this.get(this.getPreviousJob(job));

        precedent = precedent == null ? 0 : precedent;
        previous = previous == null ? 0 : previous;

        return Math.max(precedent, previous);
    }

    /**
     * Returns the largest processing time of this schedule instance
     *
     * @return The largest processing time of a schedule
     */
    public Integer getMaxValue() {
        int val = 0;
        for (int i = 0; i < size(); i++)
            val = val > getByIndex(i) ? val : getByIndex(i);
        return val;
    }

    /**
     * Get the order of jobs
     *
     * @return a list of ordered jobs
     */
    public List<Point> getIndices() {
        return indices;
    }

    /**
     * Set the order of jobs
     *
     * @param indices new list of ordered jobs
     */
    public void setIndices(List<Point> indices) {
        this.indices = new ArrayList<>(indices);
    }

}
