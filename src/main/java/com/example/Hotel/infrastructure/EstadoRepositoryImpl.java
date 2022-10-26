package com.example.Hotel.infrastructure;

import com.example.Hotel.model.Estado;
import com.example.Hotel.repositorys.EstadoRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {
    @PersistenceContext
    private EntityManager manager;
    @Override
    public List<Estado> all(){
        return manager.createQuery("from State", Estado.class).getResultList();
    }
    @Override
    public Estado perId(Long id){
        return manager.find(Estado.class, id);
    }

    @Transactional
    @Override
    public Estado add(Estado state){
        return manager.merge(state);
    }

    @Transactional
    @Override
    public void remove(Long id){
        Estado state = perId(id);
        manager.remove(state);
    }
}
