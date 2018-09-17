package com.udayalakmal.lmdeliveries.model;

import java.io.Serializable;

/**
 * Created by Lakmal on 9/16/18.
 */
/*
[{"id":1,"description":"Deliver wine to Kenneth","imageUrl":"https://s3-ap-southeast-1.amazonaws.com/lalamove-mock-api/images/pet-8.jpeg","DLocation":{"lat":22.319181,"lng":114.170008,"address":"Mong Kok"}},
 */
public class Deliveries implements Serializable{

    private String id;
    private String description;
    private String imageUrl;
    private DLocation location;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public DLocation getLocation() {
        return location;
    }

    public void setLocation(DLocation location) {
        this.location = location;
    }




}
