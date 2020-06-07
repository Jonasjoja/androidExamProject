package com.example.destinationinspire_androidexam.Model;

public class CityModel {

    private String city;
    private String country;
    private double lat;
    private double len;

    public CityModel(String city, String country, double lat, double len) {
        this.city = city;
        this.country = country;
        this.lat = lat;
        this.len = len;
    }
    public CityModel(){

    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLen() {
        return len;
    }

    public void setLen(double len) {
        this.len = len;
    }
}
