package org.studenthousingsystem;

public class Gatekeeper extends Person {

    private String gatekeeper_id;
    private double salary;
    static private int next_id = 1;

    public Gatekeeper(String name, String email, double salary, String pId, String passwordHash) {
        super(name, email, pId, passwordHash);
        this.gatekeeper_id = String.valueOf(next_id);
        this.salary = salary;
        next_id++;
    }

    public String getGatekeeper_id() {
        return gatekeeper_id;
    }

    public void setGatekeeper_id(String gatekeeper_id) {
        this.gatekeeper_id = gatekeeper_id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
