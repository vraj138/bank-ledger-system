package dev.codescreen.transactionservice.model;

public class PingResponse {
    private String serverTime;

    public PingResponse(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}
