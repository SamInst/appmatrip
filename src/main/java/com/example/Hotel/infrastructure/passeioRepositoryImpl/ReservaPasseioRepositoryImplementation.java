package com.example.Hotel.infrastructure.passeioRepositoryImpl;

import com.example.Hotel.model.passeios.ReservarPasseio;
import com.example.Hotel.repositorys.passeioRepository.ReservarPasseioRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class ReservaPasseioRepositoryImplementation implements ReservarPasseioRepository {
    @PersistenceContext
    private EntityManager manager;
    @Override
    public List<ReservarPasseio> all(){
        return manager.createQuery("from ReservarPasseio", ReservarPasseio.class).getResultList();
    }
    @Override
    public ReservarPasseio perId(Long id){ return manager.find(ReservarPasseio.class, id);
    }
    @Transactional
    @Override
    public ReservarPasseio add(ReservarPasseio passeio){
        return manager.merge(passeio);
    }

    @Transactional
    @Override
    public void remove(Long id){
        ReservarPasseio passeio = perId(id);
        manager.remove(passeio);
    }
}
