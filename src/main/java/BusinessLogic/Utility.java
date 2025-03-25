package BusinessLogic;

import DataModel.Employee;
import DataModel.Task;

import java.util.*;

public class Utility {
    private TaskManagement taskManagement;

    public Utility(TaskManagement taskManagement) {
        this.taskManagement = taskManagement;
    }

   public String ascEmployeeWorkedHours() {
    StringBuilder result = new StringBuilder();
    ArrayList<Employee> employees = taskManagement.getEmployees();
    ArrayList<Employee> employees1 = new ArrayList<>();

    for (Employee e : employees) {
        int hours = taskManagement.calculateEmployeeWorkDuration(e.getIdEmployee());
        if (hours >= 40) {
            employees1.add(e);
        }
    }

    if (employees1.isEmpty()) {
        return "No employee worked more than 40 hours";
    }

    employees1.sort((o1, o2) ->
            taskManagement.calculateEmployeeWorkDuration(o1.getIdEmployee()) -
                    taskManagement.calculateEmployeeWorkDuration(o2.getIdEmployee()));

    for (Employee employee : employees1) {
        result.append("Employee: ").append(employee.getName())
              .append(" worked for ").append(taskManagement.calculateEmployeeWorkDuration(employee.getIdEmployee()))
              .append(" hours\n");
    }

    return result.toString();
}

    public Map<String, Map<String,Integer>> getTaskStatus(){
        List<Employee> employees = taskManagement.getEmployees();
        Map<String, Map<String,Integer>> result = new HashMap<>();
        for(Employee e: employees){
            List<Task> tasks = taskManagement.getTasksByEmployee(e.getIdEmployee());
            Map<String,Integer> statusCount = new HashMap<>();
            statusCount.put("Completed",0);
            statusCount.put("Uncompleted",0);
            for(Task t: tasks){
                if(t.getStatusTask().equals("Completed")){
                    statusCount.put("Completed",statusCount.get("Completed")+1);
                }else{
                    statusCount.put("Uncompleted",statusCount.get("Uncompleted")+1);
                }
            }
            result.put(e.getName(),statusCount);
        }
        return result;
    }
}
