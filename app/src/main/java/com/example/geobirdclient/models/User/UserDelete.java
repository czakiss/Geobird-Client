package com.example.geobirdclient.models.User;

public class UserDelete {

    private String email;

    public UserDelete(String email) {
        this.email = email;
    }
    public UserDelete(){

    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
