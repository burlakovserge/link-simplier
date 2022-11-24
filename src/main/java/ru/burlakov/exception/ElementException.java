package ru.burlakov.exception;


import lombok.Setter;

@Setter
public class ElementException extends RuntimeException {
    private Message message;

    public ElementException(Message message) {
        this.message = message;
    }

    public ElementException(String message) {
        super(message);
    }

    public String getMessage() {
        return message.getDescription();
    }
}
