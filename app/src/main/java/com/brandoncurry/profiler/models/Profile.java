package com.brandoncurry.profiler.models;

import com.brandoncurry.profiler.constants.Constants;

public class Profile {


    public String id;
    public String userId;
    public String name;
    public String age;
    public String gender;
    public String hobbies;
    public String imageUrl;
    public String backgroundColor;

    public Profile(){

    }

    public Profile(String id, String userId, String imageUrl, String name, String age, String gender, String hobbies, String bgColor) {
        this.id = id;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hobbies = hobbies;
        this.backgroundColor = bgColor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBackgroundColor() {
        if(backgroundColor!=null){
            return backgroundColor;
        } else {
            if(gender.equals(Constants.PROFILE_MALE)){
                return Constants.DEFAULT_BACKGROUND_COLOR_BLUE;
            } else {
                return Constants.DEFAULT_BACKGROUND_COLOR_PINK;
            }
        }
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}