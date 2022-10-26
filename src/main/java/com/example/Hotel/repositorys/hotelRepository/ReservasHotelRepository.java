package com.example.Hotel.repositorys.hotelRepository;

import com.example.Hotel.model.hotel.ReservarHotel;
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
