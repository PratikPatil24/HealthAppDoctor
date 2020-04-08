package com.pratik.healthappdoctor.models;

import java.io.Serializable;

public class Prescription implements Serializable {
    String ID, doctorID, doctorName, doctorSpeciality, doctorDegree, date, type, medicines;

    public Prescription() {
    }

    public Prescription(String ID, String doctorID, String doctorName, String doctorSpeciality, String doctorDegree, String date, String type, String medicines) {
        this.ID = ID;
        this.doctorID = doctorID;
        this.doctorName = doctorName;
        this.doctorSpeciality = doctorSpeciality;
        this.doctorDegree = doctorDegree;
        this.date = date;
        this.type = type;
        this.medicines = medicines;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSpeciality() {
        return doctorSpeciality;
    }

    public void setDoctorSpeciality(String doctorSpeciality) {
        this.doctorSpeciality = doctorSpeciality;
    }

    public String getDoctorDegree() {
        return doctorDegree;
    }

    public void setDoctorDegree(String doctorDegree) {
        this.doctorDegree = doctorDegree;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }
}
