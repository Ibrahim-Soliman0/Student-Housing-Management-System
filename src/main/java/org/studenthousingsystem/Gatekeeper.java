package org.studenthousingsystem;

public class Gatekeeper extends Person{

    String id;

    public Gatekeeper(String name, String email, String id) {
        super(name, email);
        this.id = id;
    }
}
