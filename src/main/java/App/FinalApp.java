package App;

import BusinessLogic.TaskManagement;
import BusinessLogic.Utility;
import GUI.Controller;
import GUI.View;

import java.util.ArrayList;

public class FinalApp {
    public static void main(String[] args) {
        TaskManagement taskManagement = new TaskManagement();
        Utility utility = new Utility(taskManagement);
        View view = new View();
        Controller controller = new Controller(view, taskManagement, utility);
    }
}
