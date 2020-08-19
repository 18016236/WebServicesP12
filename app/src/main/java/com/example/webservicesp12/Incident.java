package com.example.webservicesp12;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.Date;

public class Incident implements Serializable {
    String type,Message;
    Double Latitude,Longtitude;
    Date date;

    public Incident(String type, String message, Double latitude, Double longtitude, Date date) {
        this.type = type;
        Message = message;
        Latitude = latitude;
        Longtitude = longtitude;
        this.date = date;
    }

    public Incident(String type, double latitude, double longtitude, String message) {
        this.type = type;
        this.Latitude = latitude;
        this.Longtitude = longtitude;
        this.Message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongtitude() {
        return Longtitude;
    }

    public void setLongtitude(Double longtitude) {
        Longtitude = longtitude;
    }

    @Exclude
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Incident(String type,String message,Double latitude,Double longtitude){
        this.type = type;
        this.Message = message;
        this.Latitude = latitude;
        this.Longtitude = longtitude;
    }
    @Override
    public String toString(){
        return type;

    }
}
