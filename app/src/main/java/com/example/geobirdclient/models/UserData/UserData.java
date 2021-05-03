package com.example.geobirdclient.models.UserData;

public class UserData {
    private Integer id;
    private String email;
    private String nickname;
    private Integer permission;

    public UserData(){

    }

    public UserData(Integer id, String email, String nickname, Integer permission) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.permission = permission;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }
}
