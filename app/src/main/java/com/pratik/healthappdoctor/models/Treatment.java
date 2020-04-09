package com.pratik.healthappdoctor.models;

public class Treatment {
    String ID, date, doctorId, doctorName, doctorSpeciality, doctorDegree, treatment;

    public Treatment() {
    }

    public Treatment(String ID, String date, String doctorId, String doctorName, String doctorSpeciality, String doctorDegree, String treatment) {
        this.ID = ID;
        this.date = date;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorSpeciality = doctorSpeciality;
        this.doctorDegree = doctorDegree;
        this.treatment = treatment;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
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

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}
