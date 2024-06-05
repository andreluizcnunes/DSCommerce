package com.api.dscommerce.dto;

import java.time.Instant;

public class CustomError {

        private Instant timestamp;
        private String status;
        private String error;
        private String path;

    public CustomError(Instant timestamp, String status, String error, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }
}
