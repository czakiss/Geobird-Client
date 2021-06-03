package com.example.geobirdclient.models.usertarget;

public class UserTargetDropByUser {
    private Integer idUser;

    public UserTargetDropByUser(Integer idUser) {
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
        return "UserTargetDropByTarget{" +
                "idUser=" + idUser +
                '}';
    }
}
