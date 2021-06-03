package com.example.geobirdclient.api.models.usertarget;

public class UserTargetDropResponse {
    private Integer idTarget;
    private Integer idUser;

    public UserTargetDropResponse(Integer idTarget, Integer idUser) {
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
        return "UserTargetDrop{" +
                "idTarget=" + idTarget +
                ", idUser=" + idUser +
                '}';
    }
}
