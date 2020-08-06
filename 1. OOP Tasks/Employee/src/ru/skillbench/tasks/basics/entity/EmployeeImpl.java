package ru.skillbench.tasks.basics.entity;

public class EmployeeImpl implements Employee {
    private String firstName;
    private String lastName;
    private int salary = 1000;
    private Employee manager;
    private Employee topManager;

    public EmployeeImpl() {

    }

    public int getSalary() {
        return salary;
    }

    public void increaseSalary(int value) {
        salary += value;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public String getManagerName() {
        if (this.manager == null) {
            return "No manager";
        } else {
            return this.manager.getFullName();
        }
    }

    public Employee getTopManager() {
        if (this.getManagerName().equals("No manager")) {
            return this;
        } else {
            return manager.getTopManager();
        }
    }

}
