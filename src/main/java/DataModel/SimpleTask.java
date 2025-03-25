package DataModel;

import java.io.Serializable;

public class SimpleTask extends Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private int startHour;
    private int endHour;

    public SimpleTask(int idTask, String statusTask, int startHour, int endHour) {
        super(idTask, statusTask);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int estimateDuration(){
        return endHour-startHour;
    };

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
}
