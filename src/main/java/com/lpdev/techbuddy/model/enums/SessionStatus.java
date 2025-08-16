package com.lpdev.techbuddy.model.enums;

public enum SessionStatus {

    WAITING_FOR_SCHEDULE("waiting_for_schedule"),
    SCHEDULED("scheduled"),
    COMPLETED("completed"),
    CANCELLED("cancelled");

    private String status;

    SessionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
