package com.pratik.healthappdoctor.models;

import java.io.Serializable;

public class Patient implements Serializable {
    String ID, phoneno, name, state, city, area, addressline, gender;
    float weight, height;
    int age;

    public Patient() {
    }

    public Patient(String ID, String phoneno, String name, String state, String city, String area, String addressline, String gender, float weight, float height, int age) {
        this.ID = ID;
        this.phoneno = phoneno;
        this.name = name;
        this.state = state;
        this.city = city;
        this.area = area;
        this.addressline = addressline;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.age = age;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
