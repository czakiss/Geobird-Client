package com.example.geobirdclient.models.usertarget;

public class UserTargetGetByUser {
    private Integer idUser;

    public UserTargetGetByUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "UserTargetGetByTarget{" +
                "idUser=" + idUser +
                '}';
    }
}
