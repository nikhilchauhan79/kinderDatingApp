package com.example.kinder.Model;

public class Profile {

    private String name;
    private String description;
    private int age;
    private Boolean star;
    private String imageUrl;

    public Profile(String name, String description, int age, Boolean star, String imageUrl) {
        this.name = name;
        this.description = description;
        this.age = age;
        this.star = star;
        this.imageUrl = imageUrl;
    }

    public Profile(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Boolean getStar() {
        return star;
    }

    public void setStar(Boolean star) {
        this.star = star;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
