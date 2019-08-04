package scheduler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

public class Schedule extends HashMap<Point, Integer> {     //Schedule is a custom ordered HashMap
    private java.util.List<Point> indices = new ArrayList<>();

    public Schedule(Schedule m) {
        m.forEach(this::put);
        this.setIndices(m.getIndices());
    }

    public Schedule() {
        super();
    }

    @Override
    public void forEach(BiConsumer<? super Point, ? super Integer> action) {  //forEach iterates map returning k,v by order
        for (int i = 0; i < indices.size(); i++)
            action.accept(indices.get(i), getByIndex(i));
    }

    public Integer getMaxValue(){
        int val = 0;
        for(int i = 0; i<size();i++)
            val = val > getByIndex(i) ? val : getByIndex(i);
        return val;
    }

    @Override
    public Integer put(Point key, Integer value) {
        if (this.get(key) == null)
            indices.add(key);
        return super.put(key, value);
    }

    public void putAll(Schedule map){
       map.forEach(this::put);
    }

    public static Schedule concat(Collection<? extends Schedule> list){
        Schedule out = new Schedule();
        list.forEach(out::putAll);
        return out;
    }

    public Integer putByIndex(Integer i, Integer value) {
        return this.put(indices.get(i), value);
    }

    public Integer getByIndex(Integer i) {
        return this.get(indices.get(i));
    }

    public Point getPreviousJob(Point job) {             //Machine 1 : J1, J2, J3,.. Jn-1 is the previous job of Jn
        int currentIndex = indices.indexOf(job);
        Point previousJob = null;
        while (currentIndex>0){
            currentIndex--;
            if(indices.get(currentIndex).y == job.y) {
                previousJob = indices.get(currentIndex);
                break;
            }
        }
        return previousJob;
    }

    public Point getPrecedentJob(Point job) { //M1 : J101 J201; M2 : J102 J202; precedent of J202 is J201
        int currentIndex = indices.indexOf(job);
        Point precedentJob = null;
        while (currentIndex>0){
            currentIndex--;
            if(indices.get(currentIndex).x == job.x) {
                precedentJob = indices.get(currentIndex);
                break;
            }
        }
        return precedentJob;
    }

    public java.util.List<Point> getIndices() {
        return indices;
    }

    public void setIndices(List<Point> indices) {
        this.indices = new ArrayList<>(indices);
    }

}
