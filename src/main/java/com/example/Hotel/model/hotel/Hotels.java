package com.example.Hotel.model.hotel;

import com.example.Hotel.model.outros.Categoria;
import com.example.Hotel.model.outros.Cidade;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table (name = "tb_hotels")
public class Hotels {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "categoria")
    private Categoria categoria;

    @Column(name = "name")
    private String name;
    @Column(name = "employee_identification_number")
    private String ein;
    @Column(name = "address")
    private String Address;

    private String phone;

    @Column(name = "hotel_description")
    private String hotelDescription;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private Cidade city;

    @Embedded
    @JoinColumn(name = "daily_prices")
    private HotelPrecos hotelPrices;

    @Embedded
    @JoinColumn(name = "number_of_rooms")
    private Quartos rooms;
    @Column(length = 5)
    private Integer star;


    @ManyToOne
    @JoinColumn(name = "foto_frontal")
    private HotelFrenteFoto hotelFrenteFoto;
//
//    @ManyToOne
//    @JoinColumn(name = "todas_fotos")
//    private HotelFotos hotelFotos;

    public HotelFrenteFoto getHotelFrenteFoto() {
        return hotelFrenteFoto;
    }

    public void setHotelFrenteFoto(HotelFrenteFoto hotelFrenteFoto) {
        this.hotelFrenteFoto = hotelFrenteFoto;
    }

//    public HotelFotos getHotelFotos() {
//        return hotelFotos;
//    }
//
//    public void setHotelFotos(HotelFotos hotelFotos) {
//        this.hotelFotos = hotelFotos;
//    }

    private Integer Destaque;

    public Integer getDestaque() {
        return Destaque;
    }

    public void setDestaque(Integer destaque) {
        Destaque = destaque;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Quartos getRooms() {
        return rooms;
    }
    public void setRooms(Quartos rooms) {
        this.rooms = rooms;
    }
    public Integer getStar() {
        return star;
    }
    public void setStar(Integer star) {
        this.star = star;
    }
    public HotelPrecos getHotelPrices() {
        return hotelPrices;
    }
    public void setHotelPrices(HotelPrecos hotelPrices) {
        this.hotelPrices = hotelPrices;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        Address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getHotelDescription() {
        return hotelDescription;
    }
    public void setHotelDescription(String hotelDescription) {
        this.hotelDescription = hotelDescription;
    }
    public Cidade getCity() {
        return city;
    }
    public void setCity(Cidade city) {
        this.city = city;
    }
    public String getEin() {
        return ein;
    }
    public void setEin(String ein) {
        this.ein = ein;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotels hotels = (Hotels) o;
        return Objects.equals(id, hotels.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
