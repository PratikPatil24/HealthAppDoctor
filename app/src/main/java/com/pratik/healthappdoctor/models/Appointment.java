package com.pratik.healthappdoctor.models;

public class Appointment {
    int number;
    String ID, pID;

    public Appointment() {
    }

    public Appointment(int number, String ID, String pID) {
        this.number = number;
        this.ID = ID;
        this.pID = pID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }
}
