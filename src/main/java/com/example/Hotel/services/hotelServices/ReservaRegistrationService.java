package com.example.Hotel.services.hotelServices;

import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.model.hotel.ReservarHotel;
import com.example.Hotel.repositorys.hotelRepository.ReservasHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ReservaRegistrationService {
    @Autowired
    private ReservasHotelRepository booksRepository;

    public ReservarHotel add(ReservarHotel books) {
        return booksRepository.add(books);
    }
    public void exclude(Long booksId) {
       try {
           booksRepository.remove(booksId);
       } catch (EmptyResultDataAccessException e){
           throw new EntityNotFound("book code % not found" + booksId);
       } catch (DataIntegrityViolationException e) {
           throw new EntityInUse("book code % could be not removed," + booksId);
       }
   }
}