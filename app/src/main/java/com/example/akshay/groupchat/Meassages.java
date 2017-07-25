package com.example.akshay.groupchat;

/**
 * Created by Akshay on 7/25/2017.
 */

public class Meassages {
   String uid;

    public Meassages() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String message;
    String email;
}
