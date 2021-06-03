package com.example.geobirdclient.models.usertarget;

import java.util.Date;

public class UserTarget {
    private Integer idUser;
    private Integer idTarget;
    private Date date;

    public UserTarget(Integer idUser, Integer idTarget, Date date) {
        this.idUser = idUser;
        this.idTarget = idTarget;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserTarget{" +
                "idUser=" + idUser +
                ", idTarget=" + idTarget +
                ", date=" + date +
                '}';
    }
}
