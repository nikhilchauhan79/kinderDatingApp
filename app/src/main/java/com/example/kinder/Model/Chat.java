package com.example.kinder.Model;

public class Chat {

    private String sender;
    private String receiver;
    private String message;
    private String imageUrl;

    public Chat(String sender, String receiver, String message, String imageUrl) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.imageUrl = imageUrl;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
