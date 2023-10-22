package com.currency.currency_module.model;
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


    @Column(name = "username",unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "level")
    private int level;

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
    private Date entryDate;

    @Column(name = "expire_date")
    private Date expireDate;

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



    @ManyToOne
    @JoinColumn(name = "airport_list_id")
    private AirportList airportList;


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

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
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

    public Date getEntryDate() {
        return this.entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(Date expireDate) {
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


        @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities=new HashSet<>();
 
            var role=new SimpleGrantedAuthority(fname);
           authorities.add(role);
      
        return authorities;
    }



    // Constructors, getters, and setters
}
