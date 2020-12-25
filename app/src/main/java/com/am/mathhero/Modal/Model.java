package com.am.mathhero.Modal;

import java.io.Serializable;

public class Model implements Serializable {
    String userName, image,country;
    Long score,countryScore;

    public Model(String userName, String image, Long score,Long countryScore, String country) {
        this.userName = userName;
        this.image = image;
        this.score = score;
        this.countryScore = countryScore;
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

    public void setScore(Long score) { this.score = score; }

    public Long getCountryScore() {
        return countryScore;
    }

    public void setCountryScore(Long countryScore) {
        this.countryScore = countryScore;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
