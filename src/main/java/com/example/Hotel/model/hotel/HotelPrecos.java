package com.example.Hotel.model.hotel;

import javax.persistence.*;

//@Entity
@Embeddable
@Table(name = "tb_hotel_prices")
public class HotelPrecos {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    private Long id;

    @Column(name = "price_only_person")
    private Float priceOne;

    @Column(name = "price_two_persons")
    private Float priceTwo;

    @Column(name = "price_three_persons")
    private Float priceThree;

    @Column(name = "price_four_persons")
    private Float priceFour;

    @Column(name = "price_five_persons")
    private Float priceFive;

//    @ManyToOne
//    @JoinColumn(name = "price_hotel")
//    private Hotels hotel;

    public Float getPriceOne() {
        return priceOne;
    }

    public void setPriceOne(Float priceOne) {
        this.priceOne = priceOne;
    }

    public Float getPriceTwo() {
        return priceTwo;
    }

    public void setPriceTwo(Float priceTwo) {
        this.priceTwo = priceTwo;
    }

    public Float getPriceThree() {
        return priceThree;
    }

    public void setPriceThree(Float priceThree) {
        this.priceThree = priceThree;
    }

    public Float getPriceFour() {
        return priceFour;
    }

    public void setPriceFour(Float priceFour) {
        this.priceFour = priceFour;
    }

    public Float getPriceFive() {
        return priceFive;
    }

    public void setPriceFive(Float priceFive) {
        this.priceFive = priceFive;
    }

//    public Hotels getHotel() {
//        return hotel;
//    }
//
//    public void setHotel(Hotels hotel) {
//        this.hotel = hotel;
//    }

//    public void setId(Long id) {
//        this.id = id;
//    }
//
//
//    public Long getId() {
//        return id;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        HotelPrices that = (HotelPrices) o;
//        return Objects.equals(id, that.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}
