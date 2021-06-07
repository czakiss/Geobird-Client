package com.example.geobirdclient.api.models.usertarget;

import java.util.Date;

public class UserTargetAddDTO {
    private Integer idUser;
    private Integer idTarget;

    public UserTargetAddDTO(Integer idUser, Integer idTarget) {
        this.idUser = idUser;
        this.idTarget = idTarget;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(Integer idTarget) {
        this.idTarget = idTarget;
    }

    @Override
    public String toString() {
        return "UserTarget{" +
                "idUser=" + idUser +
                ", idTarget=" + idTarget +
                '}';
    }
}
