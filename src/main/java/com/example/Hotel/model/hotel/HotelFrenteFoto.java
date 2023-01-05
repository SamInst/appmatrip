package com.example.Hotel.model.hotel;

import javax.persistence.*;

@Entity
@Table(name = "BI02_FOTO_FRONTAL")
@SequenceGenerator(name = "BI02_FOTO_FRONTAL_SEQ", sequenceName = "BI02_FOTO_FRONTAL_SEQ", allocationSize = 1)
public class HotelFrenteFoto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BI02_FOTO_FRONTAL_SEQ")
    @Column(name = "BI02_COD_FOTO_BIBLIOTECA")
    private Long id;

    @Column(name = "BI02_PATH")
    private String path;

    @Column(name = "image_title", length = 99999)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToOne
    @JoinColumn(name = "fk_frontal_photo")
    private Hotels hotels;

    public HotelFrenteFoto(String path, String title, Hotels hotels) {
        this.path = path;
        this.title = title;
        this.hotels = hotels;
    }

    public HotelFrenteFoto(String path) {this.path = path;}
    public HotelFrenteFoto(Long id, String path) {this.id = id;this.path = path;}
    public HotelFrenteFoto() {}
    public Long getId() {return id;}
    public String getPath() {return path;}
    public void setPath(String path) {this.path = path;}

    public Hotels getHotels() {
        return hotels;
    }

    public void setHotels(Hotels hotels) {
        this.hotels = hotels;
    }
}