package ru.burlakov.exception;

public enum Message {

    SHORT_LINK_NOT_FOUND("short link not found");

    public String description;

    Message(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
