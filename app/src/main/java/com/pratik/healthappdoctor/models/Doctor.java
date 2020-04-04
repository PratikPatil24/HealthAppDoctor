package com.pratik.healthappdoctor.models;

public class Doctor {
    String phoneno, name, speciality, degree, state, city, area, addressline, gender;
    int age;

    public Doctor() {
    }

    public Doctor(String phoneno, String name, String speciality, String degree, String state, String city, String area, String addressline, String gender, int age) {
        this.phoneno = phoneno;
        this.name = name;
        this.speciality = speciality;
        this.degree = degree;
        this.state = state;
        this.city = city;
        this.area = area;
        this.addressline = addressline;
        this.gender = gender;
        this.age = age;
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

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
