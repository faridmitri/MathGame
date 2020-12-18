package com.am.mathhero;

import java.io.Serializable;

public class Model implements Serializable {
    String name, profileUrl, score,country;

    public Model(String name, String profileUrl, String score, String country) {
        this.name = name;
        this.profileUrl = profileUrl;
        this.score = score;
        this.country = country;
    }

    public Model() {}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profileUrl;
    }

    public void setProfile(String profile) {
        this.profileUrl = profile;
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
