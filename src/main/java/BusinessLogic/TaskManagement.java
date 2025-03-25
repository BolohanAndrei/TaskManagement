package BusinessLogic;

import DataModel.ComplexTask;
import DataModel.Employee;
import DataModel.Task;

import java.io.*;
import java.util.*;

public class TaskManagement {
    private Map<Employee, List<Task>> map;
    private List<Task> tasks;

    public TaskManagement(){
        map = new HashMap<Employee, List<Task>>();
        tasks = new ArrayList<Task>();
    }

    public void assignTaskToEmployee(int idEmployee, Task task){
        for(Employee employee : map.keySet()){
            if(employee.getIdEmployee() == idEmployee){
                if(!map.get(employee).contains(task)) {
                    map.get(employee).add(task);
                }
                if(task instanceof ComplexTask){
                    assignSubTasksToEmployee(employee,(ComplexTask) task);
                }

                return;
            }
        }

    }

    private void assignSubTasksToEmployee(Employee employee, ComplexTask task){
        for(Task subTask : task.getSubTasks()){
            if(!map.get(employee).contains(subTask)) {
                map.get(employee).add(subTask);
            }
            if(subTask instanceof ComplexTask){
                assignSubTasksToEmployee(employee,(ComplexTask) subTask);
            }
        }
    }

    public List<Task> getTasksByEmployee(int idEmployee){
        for(Employee employee : map.keySet()){
            if(employee.getIdEmployee() == idEmployee){
                return map.get(employee);
            }
        }
        return new ArrayList<>();
    }

    public int calculateEmployeeWorkDuration(int idEmployee) {
    int totalDuration = 0;
    List<Task> tasks = getTasksByEmployee(idEmployee);
    for (Task task : tasks) {
        totalDuration += task.estimateDuration();
    }
    return totalDuration;
}

    public void modifyTaskStatus(int idEmployee,int idTask,String status){
        for(Employee employee : map.keySet()){
            if(employee.getIdEmployee() == idEmployee){
                List<Task> employeeTasks = map.get(employee);
                for(Task task : employeeTasks){
                    if(task.getIdTask() == idTask){
                        task.setStatusTask(status);
                        return;
                    }
                }
            }
        }

    }

    public ArrayList<Employee> getEmployees(){
        ArrayList<Employee> employees = new ArrayList<Employee>();
        for(Employee employee : map.keySet()){
            if(employee!=null) {
                employees.add(employee);
            }
        }
        return employees;
    }


    public void addEmployee(int employeeId, String employeeName) {
        Employee employee = new Employee(employeeId, employeeName);
        map.put(employee, new ArrayList<Task>());
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getTaskById(int idTask) {
        for (Task task : tasks) {
            if (task.getIdTask() == idTask) {
                return task;
            }
        }
        return null;
    }

    public void serializeData(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(map);
            oos.writeObject(tasks);
            for(Employee employee : map.keySet()){
                oos.writeObject(employee.getIdEmployee());
            }
            for(Task task : tasks){
                oos.writeObject(task.getIdTask());
            }
        }
    }

    public void deserializeData(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            map = (Map<Employee, List<Task>>) ois.readObject();
            tasks = (List<Task>) ois.readObject();
            for(Employee employee : map.keySet()){
                ois.readObject();
            }
            for(Task task : tasks){
                ois.readObject();
            }
        }
    }
}
