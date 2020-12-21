package com.am.mathhero;

import java.io.Serializable;

public class Model implements Serializable {
    String userName, image, score,country;

    public Model(String userName, String image, String score, String country) {
        this.userName = userName;
        this.image = image;
        this.score = score;
        this.country = country;
    }

    public Model() {}


    public String getuserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getimage() {
        return image;
    }

    public void setimage(String image) {
        this.image = image;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
