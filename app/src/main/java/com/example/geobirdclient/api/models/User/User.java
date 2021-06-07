package com.example.geobirdclient.api.models.User;

 public class User {
     private Integer id;
     private String password;
     private String email;
     private String nickname;
     private Integer permission;



     public User(Integer id, String password, String email, String nickname, Integer permission) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.permission = permission;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

     @Override
     public String toString() {
         return "User{" +
                 "id=" + id +
                 ", password='" + password + '\'' +
                 ", email='" + email + '\'' +
                 ", nickname='" + nickname + '\'' +
                 ", permission=" + permission +
                 '}';
     }
}
