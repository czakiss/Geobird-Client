package com.example.geobirdclient.models.usertarget;

import java.util.Date;

public class UserTargetAddResponse {
    private String resultMessage;
    private String deleteStatus;

    public UserTargetAddResponse(String resultMessage, String deleteStatus) {
        this.resultMessage = resultMessage;
        this.deleteStatus = deleteStatus;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public String toString() {
        return "UserTargetAddResponse{" +
                "resultMessage='" + resultMessage + '\'' +
                ", deleteStatus='" + deleteStatus + '\'' +
                '}';
    }
}
