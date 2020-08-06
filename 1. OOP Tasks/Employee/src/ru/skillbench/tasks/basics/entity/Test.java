package ru.skillbench.tasks.basics.entity;

public class Test {
    public static void main(String[] args) {
        Employee emp = new EmployeeImpl();

        emp.setFirstName("John");
        emp.setLastName("Malkovich");

        System.out.println(emp.getSalary());

        emp.increaseSalary(500);
        System.out.println(emp.getSalary());

        System.out.println(emp.getFirstName());
        System.out.println(emp.getLastName());

        emp.setFirstName("Edward");
        emp.setLastName("IV");
        System.out.println(emp.getFirstName());
        System.out.println(emp.getLastName());

        Employee mngr = new EmployeeImpl();
        mngr.setFirstName("Manager");
        mngr.setLastName("Manageroff");
        emp.setManager(mngr);

        emp.getManagerName();

        Employee topMngr = new EmployeeImpl();
        topMngr.setFirstName("Topmanager");
        topMngr.setLastName("Topmanageroff");
        mngr.setManager(topMngr);

        System.out.println(mngr.getManagerName());
        System.out.println(emp.getTopManager().getFullName());

    }
}
