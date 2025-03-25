package DataModel;


import java.io.Serializable;
import java.util.*;

public class ComplexTask extends Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private int startHour;
    private int endHour;
    private List<Task> subTasks;

    public ComplexTask(int idTask, String statusTask, int startHour, int endHour) {
        super(idTask, statusTask);
        this.subTasks = new ArrayList<Task>();
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public void addTask(Task task){
        subTasks.add(task);
    }

    public void DeleteTask(Task task){
        subTasks.remove(task);
    }

    public int estimateDuration(){
        int totalDuration;
        totalDuration = endHour-startHour;
        for(Task task : subTasks){
            totalDuration += task.estimateDuration();
        }
        //return totalDuration>0?totalDuration:endHour-startHour;
        return totalDuration;
    };

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public List<Task> getSubTasks() {
        return subTasks;
    }
}
