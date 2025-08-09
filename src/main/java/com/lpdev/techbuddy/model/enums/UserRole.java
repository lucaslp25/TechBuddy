package com.lpdev.techbuddy.model.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {

    DEV_BUDDY("role_dev_buddy"),
    MENTOR_BUDDY("role_mentor_buddy");

    private String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public SimpleGrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(value);
    }
}
