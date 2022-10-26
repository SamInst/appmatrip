package com.example.Hotel.model.hotel;

import com.example.Hotel.model.Cliente;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_books")
public class ReservarHotel {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_hotel", nullable = false)
    private Hotels hotels;

    @ManyToOne
    @JoinColumn(name = "id_client",nullable = false)
    private Cliente client;

    @Column(name = "data_entry",nullable = false)
    private LocalDate dataEntry;

    @Column(name = "Data_out",nullable = false)
    private LocalDate dataOut;

    @Column(name = "amount_adult",nullable = false)
    private Integer adults;

    @Column(name = "amount_childs")
    private Integer childs;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Hotels getHotels() {return hotels;}
    public void setHotels(Hotels hotels) {this.hotels = hotels;}
    public Cliente getClient() {return client;}
    public void setClient(Cliente client) {this.client = client;}
    public LocalDate getDataEntry() {return dataEntry;}
    public void setDataEntry(LocalDate dataEntry) {this.dataEntry = dataEntry;}
    public LocalDate getDataOut() {return dataOut;}
    public void setDataOut(LocalDate dataOut) {this.dataOut = dataOut;}
    public Integer getAdults() {return adults;}
    public void setAdults(Integer adults) {this.adults = adults;}
    public Integer getChilds() {return childs;}
    public void setChilds(Integer childs) {this.childs = childs;}
}