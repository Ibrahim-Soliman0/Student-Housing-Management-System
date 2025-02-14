package org.studenthousingsystem;

public class Staff extends Person{

    String id;

    public Staff(String name, String email, String id) {
        super(name, email);
        this.id = id;
    }
}
