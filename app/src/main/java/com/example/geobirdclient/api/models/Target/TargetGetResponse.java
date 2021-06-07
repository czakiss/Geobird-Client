package com.example.geobirdclient.api.models.Target;

public class TargetGetResponse {
    private Target targetDataDTO;
    private String resultMessage;
    private String GetStatus;

    public TargetGetResponse(Target targetDataDTO, String resultMessage, String getStatus) {
        this.targetDataDTO = targetDataDTO;
        this.resultMessage = resultMessage;
        GetStatus = getStatus;
    }

    public Target getTargetDataDTO() {
        return targetDataDTO;
    }

    public void setTargetDataDTO(Target targetDataDTO) {
        this.targetDataDTO = targetDataDTO;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getGetStatus() {
        return GetStatus;
    }

    public void setGetStatus(String getStatus) {
        GetStatus = getStatus;
    }

    @Override
    public String toString() {
        return "TargetGetResponse{" +
                "targetDataDTO=" + targetDataDTO +
                ", resultMessage='" + resultMessage + '\'' +
                ", GetStatus='" + GetStatus + '\'' +
                '}';
    }
}
