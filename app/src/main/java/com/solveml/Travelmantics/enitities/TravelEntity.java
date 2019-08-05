package com.solveml.Travelmantics.enitities;

import java.io.Serializable;

public class TravelEntity implements Serializable {

    private String id;
    private String place;
    private String description;
    private String miles;
    private  String image;

    public  TravelEntity(){


    }

    public TravelEntity(String place, String description, String miles, String image) {
        //this.id = id;
        this.place = place;
        this.description = description;
        this.miles = miles;
        this.image = image;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMiles() {
        return miles;
    }

    public void setMiles(String miles) {
        this.miles = miles;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
