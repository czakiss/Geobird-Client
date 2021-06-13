package com.example.geobirdclient.api.models.usertarget;

import java.util.List;

public class UserTargetGetByResponse {
    private List<UserTarget> userTargetDataDTOS;
    private String resultMessage;
    private String getStatus;

    public UserTargetGetByResponse(List<UserTarget> userTargetDataDTOS) {
        this.userTargetDataDTOS = userTargetDataDTOS;
    }

    public List<UserTarget> getUserTargetDataDTOS() {
        return userTargetDataDTOS;
    }

    public void setUserTargetDataDTOS(List<UserTarget> userTargetDataDTOS) {
        this.userTargetDataDTOS = userTargetDataDTOS;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getGetStatus() {
        return getStatus;
    }

    public void setGetStatus(String addStatus) {
        this.getStatus = addStatus;
    }

    @Override
    public String toString() {
        return "UserTargetGetByResponse{" +
                "userTargetDataDTOS=" + userTargetDataDTOS +
                ", resultMessage='" + resultMessage + '\'' +
                ", addStatus='" + getStatus + '\'' +
                '}';
    }
}
