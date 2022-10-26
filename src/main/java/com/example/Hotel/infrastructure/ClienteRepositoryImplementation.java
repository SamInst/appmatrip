package com.example.Hotel.infrastructure;

import com.example.Hotel.model.Cliente;
import com.example.Hotel.repositorys.ClienteRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
@Component
public class ClienteRepositoryImplementation implements ClienteRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Cliente> all() { return manager.createQuery("from Client", Cliente.class).getResultList(); }

    @Override
    public Cliente perId(Long id) {return manager.find(Cliente.class, id);}

    @Transactional
    @Override
    public Cliente add(Cliente client) {
        return manager.merge(client);
    }
    @Transactional
    @Override
    public void remove(Long id) {
        Cliente client = perId(id);
        if (client == null){
            throw new EmptyResultDataAccessException(1);
        }
        manager.remove(client);
    }
}
