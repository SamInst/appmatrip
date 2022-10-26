package com.example.Hotel.model.passeios;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

//@Entity
@Embeddable
@Table(name = "tb_passeio_preco")
public class PasseiosPrecos {


    @Column(name = "preço_por_pessoa")
    private Float priceOne;


    public Float getPriceOne() {
        return priceOne;
    }

    public void setPriceOne(Float priceOne) {
        this.priceOne = priceOne;
    }




}
