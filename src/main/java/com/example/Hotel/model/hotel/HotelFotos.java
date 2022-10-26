package com.example.Hotel.model.hotel;

import javax.persistence.*;

@Entity
@Table(name = "BI02_FOTO_BIBLIOTECA")
@SequenceGenerator(name = "BI02_FOTO_BIBLIOTECA_SEQ", sequenceName = "BI02_FOTO_BIBLIOTECA_SEQ", allocationSize = 1)
public class HotelFotos {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BI02_FOTO_BIBLIOTECA_SEQ")
    @Column(name = "BI02_COD_FOTO_BIBLIOTECA")
    private Long id;

    @Column(name = "image_title")
    private String title;

    @Column(name = "BI02_PATH")
    private String path;

    @ManyToOne
    @JoinColumn(name = "fk_hotel_photos")
    private Hotels hotels;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotels getHotels() {
        return hotels;
    }

    public void setHotels(Hotels hotels) {
        this.hotels = hotels;
    }

    public HotelFotos(String path) {this.path = path;}

    public HotelFotos(String title, String path, Hotels hotels) {
        this.title = title;
        this.path = path;
        this.hotels = hotels;
    }

    public HotelFotos(Long id, String path) {this.id = id;this.path = path;}
    public HotelFotos() {}
    public Long getId() {return id;}
    public String getPath() {return path;}
    public void setPath(String path) {this.path = path;}

}