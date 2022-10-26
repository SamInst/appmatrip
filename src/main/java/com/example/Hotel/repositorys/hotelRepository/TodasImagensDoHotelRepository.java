package com.example.Hotel.repositorys.hotelRepository;

import com.example.Hotel.model.hotel.HotelFotos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodasImagensDoHotelRepository extends JpaRepository<HotelFotos, Long> {

    List<HotelFotos> findByHotels_Id(Long id);

}
