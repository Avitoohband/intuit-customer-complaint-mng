package com.intuit.complaintservice.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ComplaintExceptionResponse {

    private final int status;
    private final String message;
    private final LocalDateTime timestamp;

    public ComplaintExceptionResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getTimestamp() {
        String timestampString;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        timestampString = timestamp.format(formatter);
        return timestampString;
    }

    @Override
    public String toString() {
        return ("status=" + status +
                ", message='" + message + '\'' +
                ", timestamp=" + getTimestamp());
    }

}
