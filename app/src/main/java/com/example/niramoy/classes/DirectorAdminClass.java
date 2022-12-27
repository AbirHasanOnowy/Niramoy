package com.example.niramoy.classes;

public class DirectorAdminClass {
    private String directorEmail,directorId, directorName, hospitalID;
    private boolean isVarified;

//    public directorAdminClass(String directorEmail, String directorId, String directorName, String hospitalID, boolean isVarified ) {
//        this.isBooked = isBooked;
//        this.bookUserId = bookUserId;
//        this.isRated = false;
//        this.roomNo = roomNo;
//    }


    public DirectorAdminClass(String directorEmail, String directorId, String directorName, String hospitalID, boolean isVarified) {
        this.directorEmail = directorEmail;
        this.directorId = directorId;
        this.directorName = directorName;
        this.hospitalID = hospitalID;
        this.isVarified = isVarified;
    }
    public DirectorAdminClass(String directorEmail, String directorId, String directorName, String hospitalID) {
        this.directorEmail = directorEmail;
        this.directorId = directorId;
        this.directorName = directorName;
        this.hospitalID = hospitalID;
    }

    public String getDirectorEmail() {
        return directorEmail;
    }

    public void setDirectorEmail(String directorEmail) {
        this.directorEmail = directorEmail;
    }

    public String getDirectorId() {
        return directorId;
    }

    public void setDirectorId(String directorId) {
        this.directorId = directorId;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
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
