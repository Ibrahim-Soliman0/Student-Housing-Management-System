package org.studenthousingsystem;

public class Staff extends Person {

    private String staff_id;
    private double salary;

    public Staff(String name, String email, double salary, String pId, String passwordHash, String staff_id) {
        super(name, email, pId, passwordHash);
        this.staff_id = staff_id;
        this.salary = salary;
    }

    public Staff(String name, String email, double salary, String pId, String passwordHash) {
        super(name, email, pId, passwordHash);
        this.salary = salary;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
