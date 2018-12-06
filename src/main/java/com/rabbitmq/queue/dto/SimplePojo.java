package com.rabbitmq.queue.dto;

import java.io.Serializable;

public class SimplePojo implements Serializable {

    private static final Long serialVersionId = 1L;

    private String userName;

    private String country;

    private String city;

    public SimplePojo(String userName, String country, String city) {
        this.userName = userName;
        this.country = country;
        this.city = city;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "SimplePojo{" +
                "userName='" + userName + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
