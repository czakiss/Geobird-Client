package com.example.geobirdclient.models.usertarget;

public class UserTargetGetByTarget {
    private Integer idTarget;

    public UserTargetGetByTarget(Integer idTarget) {
        this.idTarget = idTarget;
    }

    public Integer getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(Integer idTarget) {
        this.idTarget = idTarget;
    }

    @Override
    public String toString() {
        return "UserTargetGetByTarget{" +
                "idTarget=" + idTarget +
                '}';
    }
}
