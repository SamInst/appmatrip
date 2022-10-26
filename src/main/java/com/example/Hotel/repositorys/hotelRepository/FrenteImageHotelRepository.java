package com.example.Hotel.repositorys.hotelRepository;

import com.example.Hotel.model.hotel.HotelFrenteFoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FrenteImageHotelRepository extends JpaRepository<HotelFrenteFoto, Long> {

    List<HotelFrenteFoto> findByHotels_Id(Long id);

}
