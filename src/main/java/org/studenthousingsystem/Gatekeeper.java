package org.studenthousingsystem;

public class Gatekeeper extends Person {

    private String gatekeeper_id;
    private double salary;

    public Gatekeeper(String name, String email, double salary, String pId, String passwordHash, String gatekeeper_id) {
        super(name, email, pId, passwordHash);
        this.gatekeeper_id = gatekeeper_id;
        this.salary = salary;
    }

    public Gatekeeper(String name, String email, double salary, String passwordHash) {
        super(name, email, passwordHash);
        this.salary = salary;
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
