package com.udayalakmal.lmdeliveries.model;

import java.io.Serializable;

/**
 * Created by Lakmal on 9/16/18.
 */



/*
"DLocation":{"lat":22.319181,"lng":114.170008,"address":"Mong Kok"}
 */
public class DLocation implements Serializable {

    private String lat;
    private String lng;
    private String address;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }




}
