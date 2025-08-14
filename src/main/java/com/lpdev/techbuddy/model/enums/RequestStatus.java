package com.lpdev.techbuddy.model.enums;

public enum RequestStatus {

    RECUSED("recused"),
    PENDING("pending"),
    ACCEPTED("accepted");

    private String value;

    RequestStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
