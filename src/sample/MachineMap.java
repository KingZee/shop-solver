package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

public class MachineMap extends HashMap<Integer,Integer> {    //A machine map is a linkedmap, that can also fetch values by index
    private ArrayList<Integer> indices = new ArrayList<>();     //remove() isn't implemented yet because it is never used

    public MachineMap(MachineMap m) {
        m.forEach(this::put);
        this.setIndices(m.getIndices());
        //super(m);
    }

    public MachineMap() {
        super();
    }

    @Override
    public void forEach(BiConsumer<? super Integer, ? super Integer> action) {  //forEach iterates map returning k,v by order
        for(int i=0;i<indices.size();i++)
            action.accept(indices.get(i),getByIndex(i));
    }

    @Override
    public Integer put(Integer key, Integer value) {
        if(this.get(key) == null)
            indices.add(key);
        return super.put(key, value);
    }

    public Integer putByIndex(Integer i, Integer value) {
        return this.put(indices.get(i),value);
    }

    public Integer getByIndex(Integer i) {
        return this.get(indices.get(i));
    }

    public Integer getPreviousJob(Integer job){             //Machine 1 : J1, J2, J3,.. Jn-1 is the previous job of Jn
        int previousIndex = indices.indexOf(job)-1;
        if(previousIndex>=0 && previousIndex<indices.size())
            return indices.get(indices.indexOf(job)-1);     //returns the key of the previous job
        else return null;
    }

    public static Integer getPrecedentJob(List<MachineMap> schedule, int machineIndex, int jobIndex){ //M1 : J101 J201; M2 : J102 J202; precedent of J202 is J201
        for(int i=machineIndex-1;i>=0;i--){    //iterate starting from current machine index
            Integer precedentValue = schedule.get(i).get(jobIndex);
            if(precedentValue!=null) return precedentValue;     //returns the processing time of precedent job
        }
        return null;
    }

    public ArrayList<Integer> getIndices() {
        return indices;
    }

    public void setIndices(ArrayList<Integer> indices) {
        this.indices = new ArrayList<>(indices);
    }

}
