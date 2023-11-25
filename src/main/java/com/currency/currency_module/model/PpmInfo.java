package com.currency.currency_module.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.StringBufferInputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "ppm_info")
@Component
public class PpmInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer applicationType;
    private String organization;
    private String ministry;
    private String orgRefNo;
    private String orgRefDate;
    private String ministryRefNo;
    private String ministryRefDate;
    private String applicantName;
    private String designation;
    private String unlpNo;
    private String unlpDuration;
    private String firstArrivalDate;
    private String passportNo;
    private String passDuration;
    private String visaNo;
    private String visaDuration;
    private String workDuration;
    private String contactPersonName;
    private String contMobileNo;
    private String conDesignation;
    private String conPhoneNo;
    private String ministryForeignRefNo;
    private String ministryForeignRefDate;
    private String sellerName;
    private String customPassIdNo;
    private String customPassDate;
    private String sellerDesignation;
    private String sellerVisaNo;
    private String sellerVisaDate;
    private String vehicleName;
    private String engNo;
    private String manufactureDate;
    private String registrationNo;
    private String chassisNo;
    private String cc;
    private String buyerName;
    private String buyerPassboNo;
    private String buyerPassDate;
    private String buyerDesignation;
    private String buyerVisaNo;
    private String buyerVisaDate;
    private Integer entryBy;
    private Integer editBy;
    private Timestamp entryAt;
    private String uploadFile;
    private String buyerAddress;
    private String buyerNid;
    private String buyerTin;




    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getApplicationType() {
        return this.applicationType;
    }

    public void setApplicationType(Integer applicationType) {
        this.applicationType = applicationType;
    }

    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getMinistry() {
        return this.ministry;
    }

    public void setMinistry(String ministry) {
        this.ministry = ministry;
    }

    public String getOrgRefNo() {
        return this.orgRefNo;
    }

    public void setOrgRefNo(String orgRefNo) {
        this.orgRefNo = orgRefNo;
    }

    public String getOrgRefDate() {
        return this.orgRefDate;
    }

    public void setOrgRefDate(String orgRefDate) {
        this.orgRefDate = orgRefDate;
    }

    public String getMinistryRefNo() {
        return this.ministryRefNo;
    }

    public void setMinistryRefNo(String ministryRefNo) {
        this.ministryRefNo = ministryRefNo;
    }

    public String getMinistryRefDate() {
        return this.ministryRefDate;
    }

    public void setMinistryRefDate(String ministryRefDate) {
        this.ministryRefDate = ministryRefDate;
    }

    public String getApplicantName() {
        return this.applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getUnlpNo() {
        return this.unlpNo;
    }

    public void setUnlpNo(String unlpNo) {
        this.unlpNo = unlpNo;
    }

    public String getUnlpDuration() {
        return this.unlpDuration;
    }

    public void setUnlpDuration(String unlpDuration) {
        this.unlpDuration = unlpDuration;
    }

    public String getFirstArrivalDate() {
        return this.firstArrivalDate;
    }

    public void setFirstArrivalDate(String firstArrivalDate) {
        this.firstArrivalDate = firstArrivalDate;
    }

    public String getPassportNo() {
        return this.passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPassDuration() {
        return this.passDuration;
    }

    public void setPassDuration(String passDuration) {
        this.passDuration = passDuration;
    }

    public String getVisaNo() {
        return this.visaNo;
    }

    public void setVisaNo(String visaNo) {
        this.visaNo = visaNo;
    }

    public String getVisaDuration() {
        return this.visaDuration;
    }

    public void setVisaDuration(String visaDuration) {
        this.visaDuration = visaDuration;
    }

    public String getWorkDuration() {
        return this.workDuration;
    }

    public void setWorkDuration(String workDuration) {
        this.workDuration = workDuration;
    }

    public String getContactPersonName() {
        return this.contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContMobileNo() {
        return this.contMobileNo;
    }

    public void setContMobileNo(String contMobileNo) {
        this.contMobileNo = contMobileNo;
    }

    public String getConDesignation() {
        return this.conDesignation;
    }

    public void setConDesignation(String conDesignation) {
        this.conDesignation = conDesignation;
    }

    public String getConPhoneNo() {
        return this.conPhoneNo;
    }

    public void setConPhoneNo(String conPhoneNo) {
        this.conPhoneNo = conPhoneNo;
    }

    public String getMinistryForeignRefNo() {
        return this.ministryForeignRefNo;
    }

    public void setMinistryForeignRefNo(String ministryForeignRefNo) {
        this.ministryForeignRefNo = ministryForeignRefNo;
    }

    public String getMinistryForeignRefDate() {
        return this.ministryForeignRefDate;
    }

    public void setMinistryForeignRefDate(String ministryForeignRefDate) {
        this.ministryForeignRefDate = ministryForeignRefDate;
    }

    public String getSellerName() {
        return this.sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getCustomPassIdNo() {
        return this.customPassIdNo;
    }

    public void setCustomPassIdNo(String customPassIdNo) {
        this.customPassIdNo = customPassIdNo;
    }

    public String getCustomPassDate() {
        return this.customPassDate;
    }

    public void setCustomPassDate(String customPassDate) {
        this.customPassDate = customPassDate;
    }

    public String getSellerDesignation() {
        return this.sellerDesignation;
    }

    public void setSellerDesignation(String sellerDesignation) {
        this.sellerDesignation = sellerDesignation;
    }

    public String getSellerVisaNo() {
        return this.sellerVisaNo;
    }

    public void setSellerVisaNo(String sellerVisaNo) {
        this.sellerVisaNo = sellerVisaNo;
    }

    public String getSellerVisaDate() {
        return this.sellerVisaDate;
    }

    public void setSellerVisaDate(String sellerVisaDate) {
        this.sellerVisaDate = sellerVisaDate;
    }

    public String getVehicleName() {
        return this.vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getEngNo() {
        return this.engNo;
    }

    public void setEngNo(String engNo) {
        this.engNo = engNo;
    }

    public String getManufactureDate() {
        return this.manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getRegistrationNo() {
        return this.registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getChassisNo() {
        return this.chassisNo;
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo = chassisNo;
    }

    public String getCc() {
        return this.cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBuyerName() {
        return this.buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPassboNo() {
        return this.buyerPassboNo;
    }

    public void setBuyerPassboNo(String buyerPassboNo) {
        this.buyerPassboNo = buyerPassboNo;
    }

    public String getBuyerPassDate() {
        return this.buyerPassDate;
    }

    public void setBuyerPassDate(String buyerPassDate) {
        this.buyerPassDate = buyerPassDate;
    }

    public String getBuyerDesignation() {
        return this.buyerDesignation;
    }

    public void setBuyerDesignation(String buyerDesignation) {
        this.buyerDesignation = buyerDesignation;
    }

    public String getBuyerVisaNo() {
        return this.buyerVisaNo;
    }

    public void setBuyerVisaNo(String buyerVisaNo) {
        this.buyerVisaNo = buyerVisaNo;
    }

    public String getBuyerVisaDate() {
        return this.buyerVisaDate;
    }

    public void setBuyerVisaDate(String buyerVisaDate) {
        this.buyerVisaDate = buyerVisaDate;
    }

    public Integer getEntryBy() {
        return this.entryBy;
    }

    public void setEntryBy(Integer entryBy) {
        this.entryBy = entryBy;
    }

    public Integer getEditBy() {
        return this.editBy;
    }

    public void setEditBy(Integer editBy) {
        this.editBy = editBy;
    }

    public Timestamp getEntryAt() {
        return this.entryAt;
    }

    public void setEntryAt(Timestamp entryAt) {
        this.entryAt = entryAt;
    }

    public String getUploadFile() {
        return this.uploadFile;
    }

    public void setUploadFile(String uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getBuyerAddress() {
        return this.buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getBuyerNid() {
        return this.buyerNid;
    }

    public void setBuyerNid(String buyerNid) {
        this.buyerNid = buyerNid;
    }

    public String getBuyerTin() {
        return this.buyerTin;
    }

    public void setBuyerTin(String buyerTin) {
        this.buyerTin = buyerTin;
    }



}
