package com.example.kinder.Model;

public class ProfileDetail {
    private String imageUrl;
    private String name;


    public ProfileDetail(String imageUrl, String name) {
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public ProfileDetail(){

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

}
