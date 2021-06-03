package com.example.geobirdclient.models.usertarget;

public class UserTargetGet {
    private Integer idTarget;
    private Integer idUser;

    public UserTargetGet(Integer idTarget, Integer idUser) {
        this.idTarget = idTarget;
        this.idUser = idUser;
    }

    public Integer getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(Integer idTarget) {
        this.idTarget = idTarget;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "UserTargetGet{" +
                "idTarget=" + idTarget +
                ", idUser=" + idUser +
                '}';
    }
}
