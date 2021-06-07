package com.example.geobirdclient.api.models.usertarget;

public class UserTargetGetResponse {
    private UserTarget userTargetDataDTO;
    private String resultMessage;
    private String addStatus;

    public UserTargetGetResponse(UserTarget userTargetDataDTO, String resultMessage, String addStatus) {
        this.userTargetDataDTO = userTargetDataDTO;
        this.resultMessage = resultMessage;
        this.addStatus = addStatus;
    }

    public UserTarget getUserTargetDataDTO() {
        return userTargetDataDTO;
    }

    public void setUserTargetDataDTO(UserTarget userTargetDataDTO) {
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
        return "UserTargetAddResponse{" +
                "userTargetDataDTO=" + userTargetDataDTO +
                ", resultMessage='" + resultMessage + '\'' +
                ", addStatus='" + addStatus + '\'' +
                '}';
    }
}
