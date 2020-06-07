package com.example.destinationinspire_androidexam.Model;

public class CountryModel {
    int image; //images have unique ID's which are ints
    String country;

    public CountryModel(int image, String country) {
        this.image = image;
        this.country = country;
    }

    public int getImage() {
        return image;
    }

    public String getCountry() {
        return country;
    }
}
