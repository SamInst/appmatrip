package com.example.Hotel.repositorys.passeioRepository;

import com.example.Hotel.model.hotel.HotelPrecos;
import com.example.Hotel.model.passeios.Passeio;
import com.example.Hotel.model.passeios.PasseiosPrecos;

import javax.transaction.Transactional;
import java.util.List;

public interface PasseioPrecosRepository {
    List<PasseiosPrecos> all();

    PasseiosPrecos perId(Long id);

    @Transactional
    PasseiosPrecos add(PasseiosPrecos passeiosPrecos);

    @Transactional
    void remove(Long passeiosPrecos);
}
