package com.example.kinder.Model;

public class UploadImage {
    private String name;
    private String imageUrl;

    public UploadImage(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UploadImage(String name, String imageUrl) {
        if(name.trim().equals("")){
            name="No name";
        }
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
