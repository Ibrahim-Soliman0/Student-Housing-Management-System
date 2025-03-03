package org.studenthousingsystem;

public class Staff extends Person {

    private String staff_id;
    private double salary;
    static private int next_id = 1;

    public Staff(String name, String email, double salary, String pId, String passwordHash) {
        super(name, email, pId, passwordHash);
        this.staff_id = String.valueOf(next_id);
        this.salary = salary;
        next_id++;
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
