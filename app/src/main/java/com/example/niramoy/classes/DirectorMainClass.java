package com.example.niramoy.classes;

public class DirectorMainClass {
    private String employeeEmail,employeeId,employeeName, employeePosition, hospitalID, isVarified, employeeDept, employeeBirthdate,employeeEdu, employeeGender ;

    public String getIsVarified() {
        return isVarified;
    }

    public void setIsVarified(String isVarified) {
        this.isVarified = isVarified;
    }

    public String getEmployeeDept() {
        return employeeDept;
    }

    public void setEmployeeDept(String employeeDept) {
        this.employeeDept = employeeDept;
    }

    public String getEmployeeBirthdate() {
        return employeeBirthdate;
    }

    public void setEmployeeBirthdate(String employeeBirthdate) {
        this.employeeBirthdate = employeeBirthdate;
    }

    public String getEmployeeEdu() {
        return employeeEdu;
    }

    public void setEmployeeEdu(String employeeEdu) {
        this.employeeEdu = employeeEdu;
    }

    public String getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(String employeeGender) {
        this.employeeGender = employeeGender;
    }

    public DirectorMainClass(String employeeEmail, String employeeId, String employeeName, String employeePosition, String hospitalID, String isVarified ) {
        this.employeeEmail = employeeEmail;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.hospitalID = hospitalID;
        this.isVarified = isVarified;
    }

    public DirectorMainClass(String employeeEmail, String employeeId, String employeeName, String employeePosition, String hospitalID, String isVarified, String employeeDept, String employeeBirthdate, String employeeEdu, String employeeGender) {
        this.employeeEmail = employeeEmail;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.hospitalID = hospitalID;
        this.isVarified = isVarified;
        this.employeeDept = employeeDept;
        this.employeeBirthdate = employeeBirthdate;
        this.employeeEdu = employeeEdu;
        this.employeeGender = employeeGender;
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

    public String isVarified() {
        return isVarified;
    }

    public void setVarified(String varified) {
        isVarified = varified;
    }
}
