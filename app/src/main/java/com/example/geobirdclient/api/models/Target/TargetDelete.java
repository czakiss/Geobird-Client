package com.example.geobirdclient.api.models.Target;

public class TargetDelete {
    private String code;

    public TargetDelete(String code) {
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
        return "TargetDelete{" +
                "code='" + code + '\'' +
                '}';
    }
}
