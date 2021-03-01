package com.example.kinder.Model;

public class ChatHome {

    private String userName;
    private String receiver;
    private String message;
    private String imageUrl;

    ChatHome(){

    }

    public ChatHome(String userName, String receiver, String message, String imageUrl) {
        this.userName = userName;
        this.receiver = receiver;
        this.message = message;
        this.imageUrl = imageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
