package com.example.niramoy.classes;

public class TestClass {
    String TestName,patientId,doctorName,doctorSpecs,hospitalID,testReport,testSummery,dateAndTime;

    public TestClass(String testName, String patientId, String doctorName, String doctorSpecs, String hospitalID, String testReport, String testSummery, String dateAndTime) {
        TestName = testName;
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.doctorSpecs = doctorSpecs;
        this.hospitalID = hospitalID;
        this.testReport = testReport;
        this.testSummery = testSummery;
        this.dateAndTime = dateAndTime;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
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

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getTestReport() {
        return testReport;
    }

    public void setTestReport(String testReport) {
        this.testReport = testReport;
    }

    public String getTestSummery() {
        return testSummery;
    }

    public void setTestSummery(String testSummery) {
        this.testSummery = testSummery;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
