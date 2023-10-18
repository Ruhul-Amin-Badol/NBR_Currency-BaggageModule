package com.currency.currency_module.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "baggage_currency_add")
public class BaggageCurrencyAdd {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "baggage_id")
    private Long baggageId;

    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "currency_note_type")
    private String currencyNoteType;

    @Column(name = "Number_of_Note")
    private String numberOfNote;


    @Column(name = "currency_amount")
    private String currencyAmount;

    @Column(name = "entry_by")
    private Long entryBy;

    @Column(name = "entry_at")
    private Timestamp entryAt;



    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBaggageId() {
        return this.baggageId;
    }

    public void setBaggageId(Long baggageId) {
        this.baggageId = baggageId;
    }

    public Long getCurrencyId() {
        return this.currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return this.currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyNoteType() {
        return this.currencyNoteType;
    }

    public void setCurrencyNoteType(String currencyNoteType) {
        this.currencyNoteType = currencyNoteType;
    }

    public String getNumberOfNote() {
        return this.numberOfNote;
    }

    public void setNumberOfNote(String numberOfNote) {
        this.numberOfNote = numberOfNote;
    }

    public String getCurrencyAmount() {
        return this.currencyAmount;
    }

    public void setCurrencyAmount(String currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    public Long getEntryBy() {
        return this.entryBy;
    }

    public void setEntryBy(Long entryBy) {
        this.entryBy = entryBy;
    }

    public Timestamp getEntryAt() {
        return this.entryAt;
    }

    public void setEntryAt(Timestamp entryAt) {
        this.entryAt = entryAt;
    }
  


}
