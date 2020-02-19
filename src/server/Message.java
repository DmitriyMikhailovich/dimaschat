package server;

import java.io.Serializable;

public class Message implements Serializable {
    private final String message;
    private final MessageType messageType;

    public Message(String message, MessageType messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    public Message(String message) {
        this.message = message;
        this.messageType = null;
    }

    public String getMessage() {
        return message;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
