package com.web_apps.student_thesis_management.model;

public enum Role {
	STUDENT("Student"),
    PROFESSOR("Professor");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
