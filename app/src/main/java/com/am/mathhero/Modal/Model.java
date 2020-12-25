package com.am.mathhero.Modal;

import java.io.Serializable;

public class Model implements Serializable {
    String userName, image,country;
    Long score;

    public Model(String userName, String image, Long score, String country) {
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

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
