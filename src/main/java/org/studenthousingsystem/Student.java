package org.studenthousingsystem;

public class Student extends Person {
    private String id, city;
    private int warnings = 0;
    private boolean paymentSuccessful = false;
    private int applied = 0;

    public Student(String name, String email, String id, String city) {
        super(name, email);
        this.id = id;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setWarnings(int warnings) {
        this.warnings = warnings;
    }

    public int getWarnings() {
        return warnings;
    }

    public void setPaymentSuccessful(boolean paymentSuccessful) {
        this.paymentSuccessful = paymentSuccessful;
    }

    public boolean isPaymentSuccessful() {
        return paymentSuccessful;
    }

    public void setApplied(int applied) {
        this.applied = applied;
    }
    public int getApplied() {
        return applied;
    }
}
