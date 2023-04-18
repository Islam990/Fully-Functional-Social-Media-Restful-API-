package com.islamgad.restapi.restapi.Exceptions;

import java.time.LocalDateTime;

public class ErrorDetails {

    private LocalDateTime timeStamp;
    private String msg;
    private String details;

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }


    public String getMsg() {
        return msg;
    }


    public String getDetails() {
        return details;
    }

    public ErrorDetails(LocalDateTime timeStamp, String msg, String details) {
        this.timeStamp = timeStamp;
        this.msg = msg;
        this.details = details;
    }
}
