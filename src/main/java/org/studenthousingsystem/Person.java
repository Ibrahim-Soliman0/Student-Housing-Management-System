package org.studenthousingsystem;

import java.sql.SQLException;

abstract public class Person {

    private String id, name, email, passwordHash;

    public Person(String name, String email, String password, String pId) {
        this.id = pId;
        this.name = name;
        this.email = email;
        this.passwordHash = Database.MD5Hash(password);
    }

    public Person(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.passwordHash = Database.MD5Hash(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
