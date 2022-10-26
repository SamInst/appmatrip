package com.example.Hotel.repositorys.hotelRepository;

import com.example.Hotel.model.hotel.HotelPrecos;

import javax.transaction.Transactional;
import java.util.List;

public interface HotelPrecosRepository {
    List<HotelPrecos> all();

    HotelPrecos perId(Long id);

    @Transactional
    HotelPrecos add(HotelPrecos hotelPrices);

    @Transactional
    void remove(Long hotelPrices);
}
