package com.currency.currency_module.model;

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
@Table(name = "pvt_info")
@Component
public class PvtInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String billEntryNo;
    private String billEntryDate;
    private String orgName;
    private String passBookNo;
    private String cpbValidity;
    private String cpbHolder;
    private String vehicleRegNo;
    private String vehicleName;
    private String cc;
    private String engNo;
    private String chasisNo;
    private String yrOfManf;
    private String customHosue;
    private String remarks;
    private String barcode;
    private String entryBy;
    private String editBy;
    private String entryAt;

   

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillEntryNo() {
        return this.billEntryNo;
    }

    public void setBillEntryNo(String billEntryNo) {
        this.billEntryNo = billEntryNo;
    }

    public String getBillEntryDate() {
        return this.billEntryDate;
    }

    public void setBillEntryDate(String billEntryDate) {
        this.billEntryDate = billEntryDate;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPassBookNo() {
        return this.passBookNo;
    }

    public void setPassBookNo(String passBookNo) {
        this.passBookNo = passBookNo;
    }

    public String getCpbValidity() {
        return this.cpbValidity;
    }

    public void setCpbValidity(String cpbValidity) {
        this.cpbValidity = cpbValidity;
    }

    public String getCpbHolder() {
        return this.cpbHolder;
    }

    public void setCpbHolder(String cpbHolder) {
        this.cpbHolder = cpbHolder;
    }

    public String getVehicleRegNo() {
        return this.vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
    }

    public String getVehicleName() {
        return this.vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getCc() {
        return this.cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getEngNo() {
        return this.engNo;
    }

    public void setEngNo(String engNo) {
        this.engNo = engNo;
    }

    public String getChasisNo() {
        return this.chasisNo;
    }

    public void setChasisNo(String chasisNo) {
        this.chasisNo = chasisNo;
    }

    public String getYrOfManf() {
        return this.yrOfManf;
    }

    public void setYrOfManf(String yrOfManf) {
        this.yrOfManf = yrOfManf;
    }

    public String getCustomHosue() {
        return this.customHosue;
    }

    public void setCustomHosue(String customHosue) {
        this.customHosue = customHosue;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getEntryBy() {
        return this.entryBy;
    }

    public void setEntryBy(String entryBy) {
        this.entryBy = entryBy;
    }

    public String getEditBy() {
        return this.editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    public String getEntryAt() {
        return this.entryAt;
    }

    public void setEntryAt(String entryAt) {
        this.entryAt = entryAt;
    }

}
