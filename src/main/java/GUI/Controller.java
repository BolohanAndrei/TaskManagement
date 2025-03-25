package GUI;

import BusinessLogic.TaskManagement;
import BusinessLogic.Utility;
import DataModel.ComplexTask;
import DataModel.Employee;
import DataModel.SimpleTask;
import DataModel.Task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {
    private View view;
    private TaskManagement taskManagement;
    private Utility utility;

    public Controller(View view, TaskManagement taskManagement, Utility utility) {
        this.view = view;
        this.taskManagement = taskManagement;
        this.utility = utility;

        this.view.addEmployeeListener(new EmployeeListener());
        this.view.assignTaskListener(new AssignTaskListener());
        this.view.viewEmployeesListener(new ViewEmployeesListener());
        this.view.modifyTaskStatusListener(new ModifyTaskStatusListener());
        this.view.displayEmployees(new displayEmployees());
        this.view.viewStatisticsListener(new ViewStatisticsListener());
        this.view.addComplexTaskListener(new ComplexTaskListener());
        this.view.addSimpleTaskListener(new SimpleTaskListener());
        this.view.addSerializeListener(new SerializeListener());
        this.view.addDeserializeListener(new DeserializeListener());
        updateExistingTask();
    }

    class SerializeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                taskManagement.serializeData("data.ser");
                view.setDisplayArea("Data serialized successfully!");
            } catch (Exception ex) {
                view.setDisplayArea("Error serializing data.");
            }
        }
    }

    class DeserializeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                taskManagement.deserializeData("data.ser");
                view.setDisplayArea("Data deserialized successfully!");
                updateExistingTask();
            } catch (Exception ex) {
                view.setDisplayArea("Error deserializing data.");
            }
        }
    }


    private void updateExistingTask(){
        List<String> tasks=new ArrayList<>();
        tasks.add("New");
        for (Task task : taskManagement.getTasks()) {
            tasks.add(task.getIdTask()+"");
        }
        view.setExistingTasks(tasks);
    }

    class EmployeeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String employeeName = view.getEmployeeName();
            if (employeeName == null || employeeName.trim().isEmpty()) {
                view.setDisplayArea("Employee name cannot be empty.");
                return;
            }

            int employeeId = view.getEmployeeId();
            taskManagement.addEmployee(employeeId, employeeName);
            view.setDisplayArea("Employee " + employeeName + " added successfully " + "with the id "+employeeId);
        }
    }

    class SimpleTaskListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int taskId = view.getTaskId();
                String taskType = view.getTaskType();
                String taskStatus = view.getTaskStatus();
                int taskStartHour = view.getTaskStartHour();
                int taskEndHour = view.getTaskEndHour();

                if (taskStartHour >= taskEndHour) {
                    view.setDisplayArea("Invalid time range. Start hour must be before end hour.");
                    return;
                }

                if(taskType.equals("Simple")){
                    Task task = new SimpleTask(taskId, taskStatus, taskStartHour, taskEndHour);
                    taskManagement.addTask(task);
                    view.setDisplayArea("Simple Task " + taskId + " added successfully!");
                    updateExistingTask();
                }

            } catch (NumberFormatException ex) {
                view.setDisplayArea("Invalid task details. Please enter valid values.");
            }
        }
    }

    class ComplexTaskListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int taskId = view.getTaskId();
                String taskStatus = view.getTaskStatus();
                String taskType = view.getTaskType();
                int taskStartHour = view.getTaskStartHour();
                int taskEndHour = view.getTaskEndHour();

                if (taskStartHour >= taskEndHour) {
                    view.setDisplayArea("Invalid time range. Start hour must be before end hour.");
                    return;
                }

                ComplexTask complexTask = new ComplexTask(taskId, taskStatus, taskStartHour, taskEndHour);
                String selectedTask = view.getSelectedTask();

                if(!"New".equals(selectedTask)){
                    int selectedTaskId = Integer.parseInt(selectedTask);
                    Task task = taskManagement.getTaskById(selectedTaskId);
                    if(task != null) {
                        complexTask.addTask(task);
                    }
                }

                if(taskType.equals("Complex")) {
                    taskManagement.addTask(complexTask);
                    view.setDisplayArea("Complex Task " + taskId + " added successfully!");
                    updateExistingTask();
                }

            }catch (Exception ex) {
                view.setDisplayArea("Error adding complex task.");
            }
        }
    }

    class AssignTaskListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int employeeId = view.getEmployeeId();
                int taskId = view.getTaskId();


                Task task = taskManagement.getTaskById(taskId);
                if (task == null) {
                    view.setDisplayArea("Task not found.");
                    return;
                }

                taskManagement.assignTaskToEmployee(employeeId, task);
                view.setDisplayArea("Task " + taskId + " assigned to Employee " + employeeId);
                if(task instanceof ComplexTask){
                    for(Task subTask : ((ComplexTask) task).getSubTasks()){
                        taskManagement.assignTaskToEmployee(employeeId,subTask);
                    }
                }
            } catch (NumberFormatException ex) {
                view.setDisplayArea("Invalid input. Please enter valid numbers.");
            }
        }
    }

    class ViewEmployeesListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            StringBuilder sb = new StringBuilder();
            for (Employee employee : taskManagement.getEmployees()) {
                sb.append("Employee: ").append(employee.getName()).append("\n");
                for (Task task : taskManagement.getTasksByEmployee(employee.getIdEmployee())) {
                    sb.append("  Task ID: ").append(task.getIdTask())
                            .append(", Status: ").append(task.getStatusTask())
                            .append(", Duration: ").append(task.estimateDuration()).append("\n");
                    if(task instanceof ComplexTask){
                        for(Task subTask : ((ComplexTask) task).getSubTasks()){
                            sb.append("    SubTask ID: ").append(subTask.getIdTask())
                                    .append(", Status: ").append(subTask.getStatusTask())
                                    .append(", Duration: ").append(subTask.estimateDuration()).append("\n");
                        }
                    }
                }
                sb.append("\n");
            }
            if(sb.isEmpty())
            view.setDisplayArea( "No employees found.");
            else
                view.setDisplayEmployees(sb.toString());
        }
    }

    class ModifyTaskStatusListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

                int employeeId = view.getEmployeeId();
                int taskId = view.getTaskId();
                String status = view.getTaskStatus();

                if (status == null || status.trim().isEmpty()) {
                    view.setDisplayArea("Status cannot be empty.");
                    return;
                }

                taskManagement.modifyTaskStatus(employeeId, taskId, status);

                    view.setDisplayArea("Task status updated successfully!"+taskId);
                    updateExistingTask();

            }
        }


    class displayEmployees implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String result = utility.ascEmployeeWorkedHours();
            view.setDisplayFiltered(result);
        }
    }

    class ViewStatisticsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            StringBuilder sb = new StringBuilder();
            Map<String, Map<String, Integer>> stats = utility.getTaskStatus();
            if (stats.isEmpty()) {
                view.setDisplayStatistics("No statistics available.");
                return;
            }

            for (String employee : stats.keySet()) {
                sb.append("Employee: ").append(employee).append("\n");
                Map<String, Integer> statusCount = stats.get(employee);
                sb.append("  Completed: ").append(statusCount.getOrDefault("Completed", 0)).append("\n");
                sb.append("  Uncompleted: ").append(statusCount.getOrDefault("Uncompleted", 0)).append("\n");
            }
            view.setDisplayStatistics(sb.toString());
        }
    }
}