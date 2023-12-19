package com.currency.currency_module.model;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Component
public class UserActivityManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;


    @Column(name = "username", unique = true)
    private String username;
    
    @Column(name = "organization_id")
    private String organizationId;

    @Column(name = "password")
    private String password;

    @Column(name = "level")
    private String level;

    @Column(name = "fname")
    private String fname;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "designation")
    private String designation;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    @Column(name = "status")
    private String status;

    @Column(name = "group_for")
    private int groupFor;

    @Column(name = "warehouse_id")
    private int warehouseId;

    @Column(name = "cc_code")
    private int ccCode;

    @Column(name = "region_id")
    private int regionId;

    @Column(name = "zone_id")
    private int zoneId;

    @Column(name = "file")
    private String file;

    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "change_pass_status")
    private int changePassStatus;

    @Column(name = "india_user")
    private int indiaUser;
    
    @Column(name = "userMatrix", nullable = true)
    private Integer userMatrix=0;

    @Column(name = "baggageModule", nullable = true)
    private Integer baggageModule=0;

    @Column(name = "currencyModule", nullable = true)
    private Integer currencyModule=0;
    
    @Column(nullable = true)
    private Integer paymentRecord=0;

    @Column(nullable = true)
    private Integer port=0;

    @Column(nullable = true)
    private Integer paymentHistory=0;

    @Column(nullable = true)
    private String Signature;


    
    @ManyToOne
    @JoinColumn(name = "airport_list_id")
    private AirportList airportList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<ModuleRole> roles;


    public Set<ModuleRole> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<ModuleRole> roles) {
        this.roles = roles;
    }



    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFname() {
        return this.fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LocalDateTime getEntryDate() {
        return this.entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDateTime getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getGroupFor() {
        return this.groupFor;
    }

    public void setGroupFor(int groupFor) {
        this.groupFor = groupFor;
    }

    public int getWarehouseId() {
        return this.warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getCcCode() {
        return this.ccCode;
    }

    public void setCcCode(int ccCode) {
        this.ccCode = ccCode;
    }

    public int getRegionId() {
        return this.regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getZoneId() {
        return this.zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public String getFile() {
        return this.file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getChangePassStatus() {
        return this.changePassStatus;
    }

    public void setChangePassStatus(int changePassStatus) {
        this.changePassStatus = changePassStatus;
    }

    public int getIndiaUser() {
        return this.indiaUser;
    }

    public void setIndiaUser(int indiaUser) {
        this.indiaUser = indiaUser;
    }

    public AirportList getAirportList() {
        return this.airportList;
    }

    public void setAirportList(AirportList airportList) {
        this.airportList = airportList;
    }

    
    public Integer getPaymentRecord() {
        return this.paymentRecord;
    }

    public void setPaymentRecord(Integer paymentRecord) {
        this.paymentRecord = paymentRecord;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getPaymentHistory() {
        return this.paymentHistory;
    }

    public void setPaymentHistory(Integer paymentHistory) {
        this.paymentHistory = paymentHistory;
    }

    
    public String getSignature() {
        return this.Signature;
    }

    public void setSignature(String Signature) {
        this.Signature = Signature;
    }
    
    public int getUserMatrix() {
        return this.userMatrix;
    }

    public void setUserMatrix(int userMatrix) {
        this.userMatrix = userMatrix;
    }

    public int getBaggageModule() {
        return this.baggageModule;
    }

    public void setBaggageModule(int baggageModule) {
        this.baggageModule = baggageModule;
    }

    public int getCurrencyModule() {
        return this.currencyModule;
    }

    public void setCurrencyModule(int currencyModule) {
        this.currencyModule = currencyModule;
    }

        public String getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }



    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities=new HashSet<>();
         for (ModuleRole role : roles) {
            var hhh=new SimpleGrantedAuthority(role.getName());
           authorities.add(hhh);
         }
        return authorities;
    }



    // Constructors, getters, and setters
}