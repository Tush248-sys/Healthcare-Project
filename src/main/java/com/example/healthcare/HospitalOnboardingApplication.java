package com.example.healthcare;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class HospitalOnboardingApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String applicationId;

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

    // Digital infrastructure
    private boolean hasComputers;
    private boolean hasInternet;
    private boolean hasPrinterScanner;
    private boolean hasAbhaIntegration;
    private boolean hasItStaff;
    private String infraNotes;

    // Document file names (stored on disk)
    private String registrationCertificateFile;
    private String nodalIdProofFile;
    private String sealAuthorizationFile;
    private String infraReadinessFile;

    // Departments configuration
    @ElementCollection
    @CollectionTable(name = "hospital_departments", joinColumns = @JoinColumn(name = "application_id"))
    private List<DepartmentConfig> departments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private HospitalApplicationStatus status;

    private LocalDateTime submittedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
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

    public String getRegistrationCertificateFile() {
        return registrationCertificateFile;
    }

    public void setRegistrationCertificateFile(String registrationCertificateFile) {
        this.registrationCertificateFile = registrationCertificateFile;
    }

    public String getNodalIdProofFile() {
        return nodalIdProofFile;
    }

    public void setNodalIdProofFile(String nodalIdProofFile) {
        this.nodalIdProofFile = nodalIdProofFile;
    }

    public String getSealAuthorizationFile() {
        return sealAuthorizationFile;
    }

    public void setSealAuthorizationFile(String sealAuthorizationFile) {
        this.sealAuthorizationFile = sealAuthorizationFile;
    }

    public String getInfraReadinessFile() {
        return infraReadinessFile;
    }

    public void setInfraReadinessFile(String infraReadinessFile) {
        this.infraReadinessFile = infraReadinessFile;
    }

    public List<DepartmentConfig> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentConfig> departments) {
        this.departments = departments;
    }

    public HospitalApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(HospitalApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
