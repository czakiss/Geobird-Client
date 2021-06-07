package com.example.geobirdclient.api.models.Target;

public class TargetResponse {
    private Target target;
    private String resultMessage;
    private String status;

    public TargetResponse(Target targetX, String resultMessage, String status) {
        this.target = targetX;
        this.resultMessage = resultMessage;
        this.status = status;
    }

    public Target getTargetX() {
        return target;
    }

    public void setTargetX(Target targetX) {
        this.target = targetX;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TargetResponse{" +
                "target=" + target +
                ", resultMessage='" + resultMessage + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
