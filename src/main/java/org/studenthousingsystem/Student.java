package org.studenthousingsystem;

public class Student extends Person {
    private String student_id, city;
    private int warnings = 0;
    private boolean paymentSuccessful = false, applied_to_room = false;

    public Student(String student_id, String name, String email, String city, String password_hash, int warnings, boolean paymentSuccessful, boolean applied_to_room, String p_Id) {
        super(name, email, password_hash, p_Id);
        this.student_id = student_id;
        this.city = city;
        this.warnings = warnings;
        this.paymentSuccessful = paymentSuccessful;
        this.applied_to_room = applied_to_room;
    }

    public Student(String student_id, String name, String email, String city, String password_hash, int warnings, boolean paymentSuccessful, boolean applied_to_room) {
        super(name, email, password_hash);
        this.student_id = student_id;
        this.city = city;
        this.warnings = warnings;
        this.paymentSuccessful = paymentSuccessful;
        this.applied_to_room = applied_to_room;
    }

    public String getStudentId() {
        return student_id;
    }

    public void setStudentId(String student_id) {
        this.student_id = student_id;
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

    public void setAppliedToRoom(boolean applied_to_room) {
        this.applied_to_room = applied_to_room;
    }

    public boolean isAppliedToRoom() {
        return applied_to_room;
    }
}
