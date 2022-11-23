package com.example.Hotel.model.hotel;

import com.example.Hotel.model.passeios.Passeio;
import javax.persistence.*;

@Entity
@Table(name = "tb_packs")
public class Packages {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn (name = "hotel_id")
    private Hotels hotels;

    public Hotels getHotels() {
        return hotels;
    }

    public void setHotels(Hotels hotels) {
        this.hotels = hotels;
    }
    @ManyToOne
    @JoinColumn (name = "passeio_id")
    private Passeio passeios;

    public Passeio getPasseios() {
        return passeios;
    }

    public void setPasseios(Passeio passeios) {
        this.passeios = passeios;
    }
}
