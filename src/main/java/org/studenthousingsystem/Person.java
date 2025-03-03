package org.studenthousingsystem;

import java.sql.SQLException;

abstract public class Person {

    private String id, name, email, passwordHash;
    static private int next_id = 1;

    public Person(String name, String email, String password, String pId) {
        try
        {
            next_id = Database.personSize();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        if (next_id == 0)
            next_id = 1;

        if (pId.isEmpty())
            this.id = String.valueOf(next_id);
        else
            this.id = pId;
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
