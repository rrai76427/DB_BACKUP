package com.crl.nms.messages;

public class CronMessage {
    String type;
    String time;

    public CronMessage() {
    }

    public CronMessage(String type, String time) {
        this.type = type;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

