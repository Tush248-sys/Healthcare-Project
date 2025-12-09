package com.example.healthcare;

import java.util.ArrayList;
import java.util.List;

public class HospitalOnboardingForm {

    // Hospital information
    private String hospitalName;
    private String hospitalType;
    private String hospitalCategory;
    private String registrationNumber;
    private String yearOfEstablishment;

    // Location
    private String address;
    private String state;
    private String district;
    private String pinCode;
    private String geoLocation;

    // Contact
    private String primaryContactNumber;
    private String officialEmail;
    private String secondaryContactNumber;

    // Nodal officer
    private String nodalName;
    private String nodalDesignation;
    private String nodalMobile;
    private String nodalEmail;

    // Digital infra
    private boolean hasComputers;
    private boolean hasInternet;
    private boolean hasPrinterScanner;
    private boolean hasAbhaIntegration;
    private boolean hasItStaff;
    private String infraNotes;

    // Departments list for dynamic rows
    private List<DepartmentConfig> departments = new ArrayList<>();

    public HospitalOnboardingForm() {
        // initialise with one empty department row for UI convenience
        this.departments.add(new DepartmentConfig());
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    public String getHospitalCategory() {
        return hospitalCategory;
    }

    public void setHospitalCategory(String hospitalCategory) {
        this.hospitalCategory = hospitalCategory;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getYearOfEstablishment() {
        return yearOfEstablishment;
    }

    public void setYearOfEstablishment(String yearOfEstablishment) {
        this.yearOfEstablishment = yearOfEstablishment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getPrimaryContactNumber() {
        return primaryContactNumber;
    }

    public void setPrimaryContactNumber(String primaryContactNumber) {
        this.primaryContactNumber = primaryContactNumber;
    }

    public String getOfficialEmail() {
        return officialEmail;
    }

    public void setOfficialEmail(String officialEmail) {
        this.officialEmail = officialEmail;
    }

    public String getSecondaryContactNumber() {
        return secondaryContactNumber;
    }

    public void setSecondaryContactNumber(String secondaryContactNumber) {
        this.secondaryContactNumber = secondaryContactNumber;
    }

    public String getNodalName() {
        return nodalName;
    }

    public void setNodalName(String nodalName) {
        this.nodalName = nodalName;
    }

    public String getNodalDesignation() {
        return nodalDesignation;
    }

    public void setNodalDesignation(String nodalDesignation) {
        this.nodalDesignation = nodalDesignation;
    }

    public String getNodalMobile() {
        return nodalMobile;
    }

    public void setNodalMobile(String nodalMobile) {
        this.nodalMobile = nodalMobile;
    }

    public String getNodalEmail() {
        return nodalEmail;
    }

    public void setNodalEmail(String nodalEmail) {
        this.nodalEmail = nodalEmail;
    }

    public boolean isHasComputers() {
        return hasComputers;
    }

    public void setHasComputers(boolean hasComputers) {
        this.hasComputers = hasComputers;
    }

    public boolean isHasInternet() {
        return hasInternet;
    }

    public void setHasInternet(boolean hasInternet) {
        this.hasInternet = hasInternet;
    }

    public boolean isHasPrinterScanner() {
        return hasPrinterScanner;
    }

    public void setHasPrinterScanner(boolean hasPrinterScanner) {
        this.hasPrinterScanner = hasPrinterScanner;
    }

    public boolean isHasAbhaIntegration() {
        return hasAbhaIntegration;
    }

    public void setHasAbhaIntegration(boolean hasAbhaIntegration) {
        this.hasAbhaIntegration = hasAbhaIntegration;
    }

    public boolean isHasItStaff() {
        return hasItStaff;
    }

    public void setHasItStaff(boolean hasItStaff) {
        this.hasItStaff = hasItStaff;
    }

    public String getInfraNotes() {
        return infraNotes;
    }

    public void setInfraNotes(String infraNotes) {
        this.infraNotes = infraNotes;
    }

    public List<DepartmentConfig> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentConfig> departments) {
        this.departments = departments;
    }
}
