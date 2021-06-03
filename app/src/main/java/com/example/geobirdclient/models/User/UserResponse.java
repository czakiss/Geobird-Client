package com.example.geobirdclient.models.User;

public class UserResponse {
    private User user;
    private String resultMessage;
    private String status;

    public UserResponse(User user, String resultMessage, String status) {
        this.user = user;
        this.resultMessage = resultMessage;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        return "UserResponse{" +
                "user=" + user +
                ", resultMessage='" + resultMessage + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
