package com.brenoepic.logging;

import com.eu.habbo.Emulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Message {
    private static final Logger LOGGER = LoggerFactory.getLogger(Message.class);

    private final int fromId;
    private final String toUser;
    private final int timestamp;
    private String message;

    public Message(int fromId, String toUser, String message) {
        this.fromId = fromId;
        this.toUser = toUser;
        this.message = message;

        this.timestamp = Emulator.getIntUnixTimestamp();
    }

    public String getToUser() {
        return this.toUser;
    }

    public int getFromId() {
        return this.fromId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTimestamp() {
        return this.timestamp;
    }
}
