package com.currency.currency_module.model;

// import javax.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.persistence.Table;

import java.sql.Timestamp;

@Entity
@Table(name = "currency_declaration") // Replace "your_table_name" with your actual table name
public class CurrencyDeclaration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passenger_name")
    private String passengerName;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "passport_issue_date")
    private String passportIssueDate;

    @Column(name = "passport_issue_place", columnDefinition = "text")
    private String passportIssuePlace;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "dateofarrival")
    private String dateOfArrival;

    @Column(name = "profession")
    private String profession;

    @Column(name = "previous_country")
    private String previousCountry;

    @Column(name = "flight_no")
    private String flightNo;

    @Column(name = "address_in_bangladesh", columnDefinition = "text")
    private String addressInBangladesh;

    @Column(name = "stay_time_abroad")
    private String stayTimeAbroad;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;

    @Column(name = "entry_at")
    private Timestamp entryAt;

    @Column(name = "check_by")
    private Long checkBy;

    @Column(name = "check_at")
    private String checkAt;

    @Column(name = "conf_note", columnDefinition = "text")
    private String confNote;

    @Column(name = "reporting_note", columnDefinition = "text")
    private String reportingNote;

    @Column(name = "rejected_by")
    private Long rejectedBy;

    @Column(name = "rejected_at")
    private String rejectedAt;

    @Column(name = "entry_by")
    private Long entryBy;
    
    @Transient
    private String otherNationality;



    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassengerName() {
        return this.passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassportNumber() {
        return this.passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPassportIssueDate() {
        return this.passportIssueDate;
    }

    public void setPassportIssueDate(String passportIssueDate) {
        this.passportIssueDate = passportIssueDate;
    }

    public String getPassportIssuePlace() {
        return this.passportIssuePlace;
    }

    public void setPassportIssuePlace(String passportIssuePlace) {
        this.passportIssuePlace = passportIssuePlace;
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDateOfArrival() {
        return this.dateOfArrival;
    }

    public void setDateOfArrival(String dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public String getProfession() {
        return this.profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPreviousCountry() {
        return this.previousCountry;
    }

    public void setPreviousCountry(String previousCountry) {
        this.previousCountry = previousCountry;
    }

    public String getFlightNo() {
        return this.flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getAddressInBangladesh() {
        return this.addressInBangladesh;
    }

    public void setAddressInBangladesh(String addressInBangladesh) {
        this.addressInBangladesh = addressInBangladesh;
    }

    public String getStayTimeAbroad() {
        return this.stayTimeAbroad;
    }

    public void setStayTimeAbroad(String stayTimeAbroad) {
        this.stayTimeAbroad = stayTimeAbroad;
    }

    public String getContactNo() {
        return this.contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getEntryAt() {
        return this.entryAt;
    }

    public void setEntryAt(Timestamp entryAt) {
        this.entryAt = entryAt;
    }

    public Long getCheckBy() {
        return this.checkBy;
    }

    public void setCheckBy(Long checkBy) {
        this.checkBy = checkBy;
    }

    public String getCheckAt() {
        return this.checkAt;
    }

    public void setCheckAt(String checkAt) {
        this.checkAt = checkAt;
    }

    public String getConfNote() {
        return this.confNote;
    }

    public void setConfNote(String confNote) {
        this.confNote = confNote;
    }

    public String getReportingNote() {
        return this.reportingNote;
    }

    public void setReportingNote(String reportingNote) {
        this.reportingNote = reportingNote;
    }

    public Long getRejectedBy() {
        return this.rejectedBy;
    }

    public void setRejectedBy(Long rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public String getRejectedAt() {
        return this.rejectedAt;
    }

    public void setRejectedAt(String rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public Long getEntryBy() {
        return this.entryBy;
    }

    public void setEntryBy(Long entryBy) {
        this.entryBy = entryBy;
    }

    public String getOtherNationality() {
        return this.otherNationality;
    }

    public void setOtherNationality(String otherNationality) {
        this.otherNationality = otherNationality;
    }




}
