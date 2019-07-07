package com.paulsan.appbank.entity;

public enum MovementType {
    DEBIT("DEBIT"),
    CREDIT("CREDIT");

    private String value;

    MovementType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
