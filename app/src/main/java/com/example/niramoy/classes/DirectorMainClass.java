package com.example.niramoy.classes;

public class DirectorMainClass {
    private String employeeEmail,employeeId,employeeName, employeePosition, hospitalID;
    private boolean isVarified;

    public DirectorMainClass(String employeeEmail, String employeeId,String employeeName, String employeePosition, String hospitalID, boolean isVarified) {
        this.employeeEmail = employeeEmail;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.hospitalID = hospitalID;
        this.isVarified = isVarified;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public DirectorMainClass(String employeeEmail, String employeeId, String employeeName, String employeePosition, String hospitalID) {
        this.employeeEmail = employeeEmail;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.hospitalID = hospitalID;
    }
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public boolean isVarified() {
        return isVarified;
    }

    public void setVarified(boolean varified) {
        isVarified = varified;
    }
}
