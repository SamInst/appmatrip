package com.example.Hotel.repositorys.hotelRepository;

import com.example.Hotel.model.hotel.ReservarHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

public interface ReservasHotelRepository {
    List<ReservarHotel> all();

    ReservarHotel perId(Long id);

    @Transactional
    ReservarHotel add(ReservarHotel books);

    @Transactional
    void remove(Long books);
}
