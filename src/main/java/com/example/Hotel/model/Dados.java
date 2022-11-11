package com.example.Hotel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Dados {

    @Column(name = "data_entry",nullable = false)
    private LocalDate dataEntry;

    @Column(name = "Data_out",nullable = false)
    private LocalDate dataOut;

    @Column(name = "amount_adult",nullable = false)
    private Integer adults;

    @Column(name = "amount_childs")
    private Integer childs;
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDataEntry() {
        return dataEntry;
    }

    public void setDataEntry(LocalDate dataEntry) {
        this.dataEntry = dataEntry;
    }

    public LocalDate getDataOut() {
        return dataOut;
    }

    public void setDataOut(LocalDate dataOut) {
        this.dataOut = dataOut;
    }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChilds() {
        return childs;
    }

    public void setChilds(Integer childs) {
        this.childs = childs;
    }
}
