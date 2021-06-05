package com.example.geobirdclient.api.models.usertarget;

import java.util.List;

public class UserTargetGetByResponse {
    private List<UserTarget> userTargetDataDTO;
    private String resultMessage;
    private String addStatus;

    public UserTargetGetByResponse(List<UserTarget> userTargetDataDTO) {
        this.userTargetDataDTO = userTargetDataDTO;
    }

    public List<UserTarget> getUserTargetDataDTO() {
        return userTargetDataDTO;
    }

    public void setUserTargetDataDTO(List<UserTarget> userTargetDataDTO) {
        this.userTargetDataDTO = userTargetDataDTO;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getAddStatus() {
        return addStatus;
    }

    public void setAddStatus(String addStatus) {
        this.addStatus = addStatus;
    }

    @Override
    public String toString() {
        return "UserTargetGetByResponse{" +
                "userTargetDataDTO=" + userTargetDataDTO +
                ", resultMessage='" + resultMessage + '\'' +
                ", addStatus='" + addStatus + '\'' +
                '}';
    }
}
