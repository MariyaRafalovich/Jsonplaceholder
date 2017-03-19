package com.example.mariya.jsonplaceholder;

/**
 * Created by Mariya on 3/14/2017.
 */

public class Contact {

    private String id;
    private String name;
    private String email;
    private String username;


    public Contact(String id, String name, String email, String username) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;

    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}



