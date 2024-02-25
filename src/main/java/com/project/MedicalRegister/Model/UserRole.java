package com.project.MedicalRegister.Model;

public enum UserRole {

    ADMIN("admin"),
    HOSPITAL_ADMIN("hospital_admin"),
    DOCTOR("doctor"),
    RECEPIONIST("receptionist"),
    PATIENT("patient");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
    public String getValue() {
        return role;
    }
}
