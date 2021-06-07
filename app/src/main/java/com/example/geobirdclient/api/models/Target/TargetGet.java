package com.example.geobirdclient.api.models.Target;


public class TargetGet {
    private String code;

    public TargetGet(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "TargetGet{" +
                "code='" + code + '\'' +
                '}';
    }
}
