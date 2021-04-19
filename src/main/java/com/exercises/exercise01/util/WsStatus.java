package com.exercises.exercise01.util;

public enum WsStatus {

    OK(0),
    FAIL(1);

    private int id;

    WsStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
