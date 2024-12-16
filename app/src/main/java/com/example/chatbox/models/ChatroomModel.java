package com.example.chatbox.models;

import com.google.firebase.Timestamp;

import java.util.List;

public class ChatroomModel {
    private String chatroomId;
    private List<String> userIds;

    private String lastMessageSenderId;

    // Default constructor (required for Firebase deserialization)
    public ChatroomModel() {
        this.chatroomId = "";
        this.userIds = null;

        this.lastMessageSenderId = "";
    }

    public ChatroomModel(String chatroomId, List<String> userIds, String lastMessageSenderId) {
        this.chatroomId = chatroomId;
        this.userIds = userIds;

        this.lastMessageSenderId = lastMessageSenderId;
    }

    // Getters and Setters
    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }



    public String getLastMessageSenderId() {
        return lastMessageSenderId;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }
}