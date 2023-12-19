package com.currency.currency_module.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;







@Entity
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paymentId;
    private String officeCode;
    private Long baggageId;
    @Column(unique = true)
    private String sessionToken;
    private String status;
    private Double paidAmount;
    private String isRefund;
    


  
        // New fields
    private String calanNo;
    private String msg;
    private String transactionId;
    private String paymentDate;
    private String transactionDate;
    private String invoiceNo;
    private String invoiceDate;
    private String brCode;
    private String applicantName;
    private String applicantContactNo;
    private String totalAmount;
    private String paymentStatus;
    private String payMode;
    private String payAmount;
    private String vat;
    private String commission;
    private String scrollNo;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentId() {
        return this.paymentId;
    }
    public String getCalanNo() {
        return this.calanNo;
    }
    public String getIsRefund() {
        return this.isRefund;
    }
    
    public String getPaymentDate() {
        return this.paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setIsRefund(String isRefund) {
        this.isRefund = isRefund;
    }
    public void setCalanNo(String calanNo) {
        this.calanNo = calanNo;
    }
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getOfficeCode() {
        return this.officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public Long getBaggageId() {
        return this.baggageId;
    }

    public void setBaggageId(Long baggageId) {
        this.baggageId = baggageId;
    }

    public String getSessionToken() {
        return this.sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPaidAmount() {
        return this.paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDate() {
        return this.transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getInvoiceNo() {
        return this.invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return this.invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getBrCode() {
        return this.brCode;
    }

    public void setBrCode(String brCode) {
        this.brCode = brCode;
    }

    public String getApplicantName() {
        return this.applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantContactNo() {
        return this.applicantContactNo;
    }

    public void setApplicantContactNo(String applicantContactNo) {
        this.applicantContactNo = applicantContactNo;
    }

    public String getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPayMode() {
        return this.payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getPayAmount() {
        return this.payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getVat() {
        return this.vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getCommission() {
        return this.commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getScrollNo() {
        return this.scrollNo;
    }

    public void setScrollNo(String scrollNo) {
        this.scrollNo = scrollNo;
    }




}
