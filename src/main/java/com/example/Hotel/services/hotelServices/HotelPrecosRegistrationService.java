package com.example.Hotel.services.hotelServices;

import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.model.hotel.HotelPrecos;
import com.example.Hotel.repositorys.hotelRepository.HotelPrecosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class HotelPrecosRegistrationService {
    @Autowired
    private HotelPrecosRepository hotelPricesRepository;

    public HotelPrecos add(HotelPrecos hotelPrices) {
        return hotelPricesRepository.add(hotelPrices);
    }
    public void exclude(Long HotelPriceId) {
       try {
           hotelPricesRepository.remove(HotelPriceId);
       } catch (EmptyResultDataAccessException e){
           throw new EntityNotFound("Price code % not found" + HotelPriceId);
       } catch (DataIntegrityViolationException e) {
           throw new EntityInUse("Price code % could be not removed," + HotelPriceId);
       }
   }
}