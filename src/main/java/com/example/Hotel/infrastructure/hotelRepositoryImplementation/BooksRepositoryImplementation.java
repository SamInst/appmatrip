package com.example.Hotel.infrastructure.hotelRepositoryImplementation;

import com.example.Hotel.model.hotel.ReservarHotel;
import com.example.Hotel.repositorys.hotelRepository.ReservasHotelRepository;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class BooksRepositoryImplementation implements ReservasHotelRepository {
    @PersistenceContext
    private EntityManager manager;
    @Override
    public List<ReservarHotel> all(){
        return manager.createQuery("from Books", ReservarHotel.class).getResultList();
    }
    @Override
    public ReservarHotel perId(Long id){ return manager.find(ReservarHotel.class, id);
    }
    @Transactional
    @Override
    public ReservarHotel add(ReservarHotel books){
        return manager.merge(books);
    }

    @Transactional
    @Override
    public void remove(Long id){
        ReservarHotel books = perId(id);
        manager.remove(books);
    }
}
