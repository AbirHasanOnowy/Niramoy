package com.example.niramoy.classes;

public class PrescriptionClass {
    String patientId,doctorName,doctorSpecs,age,hospitalID,medicines,symptoms,recommendations,dateAndTime;

    public PrescriptionClass(String patientId, String doctorName, String doctorSpecs, String age, String hospitalID, String medicines, String symptoms, String recommendations, String dateAndTime) {
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.doctorSpecs = doctorSpecs;
        this.age = age;
        this.hospitalID = hospitalID;
        this.medicines = medicines;
        this.symptoms = symptoms;
        this.recommendations = recommendations;
        this.dateAndTime = dateAndTime;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSpecs() {
        return doctorSpecs;
    }

    public void setDoctorSpecs(String doctorSpecs) {
        this.doctorSpecs = doctorSpecs;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
