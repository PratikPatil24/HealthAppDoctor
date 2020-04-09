package com.pratik.healthappdoctor.models;

public class Chemist {
    String id, name, gender, state, city, area, addressline;

    public Chemist() {
    }

    public Chemist(String id, String name, String gender, String state, String city, String area, String addressline) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.state = state;
        this.city = city;
        this.area = area;
        this.addressline = addressline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddressline() {
        return addressline;
    }

    public void setAddressline(String addressline) {
        this.addressline = addressline;
    }
}

