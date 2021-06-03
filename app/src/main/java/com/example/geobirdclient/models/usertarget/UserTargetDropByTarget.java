package com.example.geobirdclient.models.usertarget;

public class UserTargetDropByTarget {
    private Integer idTarget;

    public UserTargetDropByTarget(Integer idTarget) {
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
        return "UserTargetDropByTarget{" +
                "idTarget=" + idTarget +
                '}';
    }
}
