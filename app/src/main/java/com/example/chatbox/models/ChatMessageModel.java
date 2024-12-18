package com.example.chatbox.models;

import com.google.firebase.Timestamp;

import java.util.Date;

public class ChatMessageModel {
    private String message;
    private String senderId;
    private Date timestamp;
    //private String messageId;



    public ChatMessageModel() {

    }



    public ChatMessageModel(String message, String senderId, Date timestamp) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
        //this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
