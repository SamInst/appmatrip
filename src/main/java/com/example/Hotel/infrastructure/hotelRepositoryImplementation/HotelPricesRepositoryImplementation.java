package com.example.Hotel.infrastructure.hotelRepositoryImplementation;

import com.example.Hotel.model.hotel.HotelPrecos;
import com.example.Hotel.repositorys.hotelRepository.HotelPrecosRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class HotelPricesRepositoryImplementation implements HotelPrecosRepository {
    @PersistenceContext
    private EntityManager manager;
    @Override
    public List<HotelPrecos> all(){
        return manager.createQuery("from HotelPrices", HotelPrecos.class).getResultList();
    }
    @Override
    public HotelPrecos perId(Long id){
        return manager.find(HotelPrecos.class, id);
    }

    @Transactional
    @Override
    public HotelPrecos add(HotelPrecos hotelPrices){
        return manager.merge(hotelPrices);
    }

    @Transactional
    @Override
    public void remove(Long id){
        HotelPrecos hotelPrices = perId(id);
        manager.remove(hotelPrices);
    }
}
