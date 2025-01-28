package org.studenthousingsystem;

public class Admin extends Person{

    String id;

    public Admin(String name, String email, String id) {
        super(name, email);
        this.id = id;
    }
}
