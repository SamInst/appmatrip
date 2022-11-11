package com.example.Hotel.services.hotelServices;

import com.example.Hotel.model.hotel.Hotels;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.repositorys.hotelRepository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class HotelRegistrationService {
    @Autowired
    private HotelRepository hotelRepository;

    public Hotels add(Hotels hotels) {
        return hotelRepository.save(hotels);
    }
    private void converteEsalva(String photo) {
    }

    public void exclude(Long hotelId) {
       try {
           hotelRepository.deleteById(hotelId);
       } catch (EmptyResultDataAccessException e){
           throw new EntityNotFound("Hotel code % not found" + hotelId);
       } catch (DataIntegrityViolationException e) {
           throw new EntityInUse("Hotel code % could be not removed," + hotelId);
       }
   }
}