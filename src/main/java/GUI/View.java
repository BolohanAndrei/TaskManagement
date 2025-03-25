package GUI;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View {
    private JFrame frame;
    private JTabbedPane tabbedPane;

    // Employee
    private JTextField employeeNameField,employeeIdField;
    private JButton addEmployeeButton;

    // Task
    private JTextField taskIdField, taskStartHourField, taskEndHourField;
    private JComboBox<String> taskTypeComboBox, taskStatusComboBox, existingTaskTypeComboBox;
    private JButton addTaskButton;

    // Task
    private JTextField assignEmployeeIdField, assignTaskIdField;
    private JButton assignTaskButton;

    // View Employees
    private JTextArea displayEmployeesArea;
    private JButton viewEmployeesButton;

    // Modify Task Status
    private JTextField modifyEmployeeIdField, modifyTaskIdField;
    private JComboBox<String> modifyTaskStatusComboBox;
    private JButton modifyTaskStatusButton;

    // Statistics
    private JButton filterButton;
    private JTextArea filteredArea;
    private JTextArea statisticsArea;
    private JButton viewStatisticsButton;

    //Serialize and Deserialize
    private JButton serializeButton;
    private JButton deserializeButton;

    public View() {

        setLook();

        frame = new JFrame("Task Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Add Employee", createAddEmployeePanel());
        tabbedPane.addTab("Add Task", createAddTaskPanel());
        tabbedPane.addTab("Assign Task", createAssignTaskPanel());
        tabbedPane.addTab("View Employees", createViewEmployeesPanel());
        tabbedPane.addTab("Modify Task Status", createModifyTaskPanel());
        tabbedPane.addTab("Filter", createFilterPanel());
        tabbedPane.addTab("View Statistics", createViewStatisticsPanel());
        tabbedPane.addTab("Serialize/Deserialize", createSerializePanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private void setLook(){
        Font font=new Font("SansSerif", Font.BOLD, 12);
        Color backgroundColor=new Color(255, 255, 255);
        Color foregroundColor=new Color(126, 217, 59);

        UIManager.put("Label.font", font);
        UIManager.put("Label.foreground", foregroundColor);
        UIManager.put("Panel.background", backgroundColor);
        UIManager.put("Button.font", font);
        UIManager.put("Button.foreground", foregroundColor);
        UIManager.put("Button.background", backgroundColor);
        UIManager.put("TextField.font", font);
        UIManager.put("TextField.foreground", foregroundColor);
        UIManager.put("TextField.background", backgroundColor);
        UIManager.put("ComboBox.font", font);
        UIManager.put("ComboBox.foreground", foregroundColor);
        UIManager.put("ComboBox.background", backgroundColor);
        UIManager.put("TextArea.font", font);
        UIManager.put("TextArea.foreground", foregroundColor);
        UIManager.put("TextArea.background", backgroundColor);
    }

    private JPanel createAddEmployeePanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        Font font = new Font("SansSerif", Font.BOLD, 12);

        employeeNameField = new JTextField(10);
        employeeNameField.setFont(font);
        employeeNameField.setPreferredSize(new Dimension(100, 20));
        employeeIdField = new JTextField(10);
        addEmployeeButton = new JButton("Add Employee");

        panel.add(new JLabel("Employee Name:"));
        panel.add(employeeNameField);
        panel.add(new JLabel("Employee ID:"));
        panel.add(employeeIdField);
        panel.add(new JLabel());
        panel.add(addEmployeeButton);

        return panel;
    }

    private JPanel createAddTaskPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        taskIdField = new JTextField();
        taskTypeComboBox = new JComboBox<>(new String[]{"Simple", "Complex"});
        existingTaskTypeComboBox=new JComboBox<>();
        taskStatusComboBox = new JComboBox<>(new String[]{"Uncompleted", "Completed"});
        taskStartHourField = new JTextField();
        taskEndHourField = new JTextField();
        addTaskButton = new JButton("Add Task");

        panel.add(new JLabel("Task ID:"));
        panel.add(taskIdField);
        panel.add(new JLabel());
        panel.add(new JLabel("Task Type:"));
        panel.add(taskTypeComboBox);
        panel.add(existingTaskTypeComboBox);
        panel.add(new JLabel("Task Status:"));
        panel.add(taskStatusComboBox);
        panel.add(new JLabel());
        panel.add(new JLabel("Start Hour:"));
        panel.add(taskStartHourField);
        panel.add(new JLabel());
        panel.add(new JLabel("End Hour:"));
        panel.add(taskEndHourField);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(addTaskButton);

        existingTaskTypeComboBox.setVisible(false);

        taskTypeComboBox.addActionListener(e -> {
            if(taskTypeComboBox.getSelectedItem().equals("Complex")){
                existingTaskTypeComboBox.setVisible(true);
            }else{
                existingTaskTypeComboBox.setVisible(false);
            }
        });

        return panel;
    }

    private JPanel createAssignTaskPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        assignEmployeeIdField = new JTextField();
        assignTaskIdField = new JTextField();
        assignTaskButton = new JButton("Assign Task");

        panel.add(new JLabel("Employee ID:"));
        panel.add(assignEmployeeIdField);
        panel.add(new JLabel("Task ID:"));
        panel.add(assignTaskIdField);
        panel.add(new JLabel());
        panel.add(assignTaskButton);

        return panel;
    }

    private JPanel createViewEmployeesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        displayEmployeesArea = new JTextArea();
        displayEmployeesArea.setEditable(false);
        viewEmployeesButton = new JButton("View Employees");

        panel.add(new JScrollPane(displayEmployeesArea), BorderLayout.CENTER);
        panel.add(viewEmployeesButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createModifyTaskPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        modifyEmployeeIdField = new JTextField();
        modifyTaskIdField = new JTextField();
        modifyTaskStatusComboBox = new JComboBox<>(new String[]{"Uncompleted", "Completed"});
        modifyTaskStatusButton = new JButton("Modify Status");

        panel.add(new JLabel("Employee ID:"));
        panel.add(modifyEmployeeIdField);
        panel.add(new JLabel("Task ID:"));
        panel.add(modifyTaskIdField);
        panel.add(new JLabel("New Status:"));
        panel.add(modifyTaskStatusComboBox);
        panel.add(new JLabel());
        panel.add(modifyTaskStatusButton);

        return panel;
    }

    private JPanel createViewStatisticsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        statisticsArea = new JTextArea();
        statisticsArea.setEditable(false);
        viewStatisticsButton = new JButton("View Statistics");

        panel.add(new JScrollPane(statisticsArea), BorderLayout.CENTER);
        panel.add(viewStatisticsButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        filterButton = new JButton("Filter");
        filteredArea = new JTextArea();
        filteredArea.setEditable(false);

        panel.add(new JScrollPane(filteredArea), BorderLayout.CENTER);
        panel.add(filterButton, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createSerializePanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        serializeButton = new JButton("Serialize");
        deserializeButton = new JButton("Deserialize");
        panel.add(serializeButton);
        panel.add(deserializeButton);
        return panel;
    }

    public String getEmployeeName() {
        return employeeNameField.getText();
    }

    public int getTaskId() {
        try {
            return Integer.parseInt(taskIdField.getText());
        }catch (NumberFormatException e){
            setDisplayArea("Invalid Task ID");
            return -1;
        }
    }

    public String getTaskType() {
        return (String) taskTypeComboBox.getSelectedItem();
    }

    public String getTaskStatus() {
        return (String) taskStatusComboBox.getSelectedItem();
    }

    public int getTaskStartHour() {
        return Integer.parseInt(taskStartHourField.getText());
    }

    public int getTaskEndHour() {
        return Integer.parseInt(taskEndHourField.getText());
    }

    public void displayEmployees(ActionListener listener) {
        filterButton.addActionListener(listener);
    }

    public void addEmployeeListener(ActionListener listener) {
        addEmployeeButton.addActionListener(listener);
    }

    public void addSimpleTaskListener(ActionListener listener) {
        addTaskButton.addActionListener(listener);
    }

    public void addComplexTaskListener(ActionListener listener) {
        addTaskButton.addActionListener(listener);
    }

    public void assignTaskListener(ActionListener listener) {
        assignTaskButton.addActionListener(listener);
    }

    public void viewEmployeesListener(ActionListener listener) {
        viewEmployeesButton.addActionListener(listener);
    }

    public void modifyTaskStatusListener(ActionListener listener) {
        modifyTaskStatusButton.addActionListener(listener);
    }

    public void viewStatisticsListener(ActionListener listener) {
        viewStatisticsButton.addActionListener(listener);
    }

    public void setDisplayEmployees(String text) {
        displayEmployeesArea.setText(text);
    }

    public void setDisplayStatistics(String text) {
        statisticsArea.setText(text);
    }

    public void setDisplayFiltered(String text) {
        filteredArea.setText(text);
    }

    public void setDisplayArea(String s) {
        JOptionPane.showMessageDialog(frame, s);
    }


    public int getEmployeeId() {
        try {
            return Integer.parseInt(employeeIdField.getText());
        }catch(NumberFormatException e){
            setDisplayArea("Invalid Employee ID");
            return -1;
        }
    }

    public void setExistingTasks(List <String> tasks){
        existingTaskTypeComboBox.removeAllItems();
        for(String task: tasks){
            existingTaskTypeComboBox.addItem(task);
        }
    }

    public String getSelectedTask(){
        return (String) existingTaskTypeComboBox.getSelectedItem();
    }

    public void addSerializeListener(ActionListener listener){
        serializeButton.addActionListener(listener);
    }

    public void addDeserializeListener(ActionListener listener){
        deserializeButton.addActionListener(listener);
    }
}
