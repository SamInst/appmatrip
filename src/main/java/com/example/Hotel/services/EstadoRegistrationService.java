package com.example.Hotel.services;

import com.example.Hotel.model.Estado;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.repositorys.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EstadoRegistrationService {
    @Autowired
    private EstadoRepository stateRepository;

    public Estado add(Estado state) {
        return stateRepository.save(state);
    }
    public void exclude(Long stateId) {
       try {
           stateRepository.deleteById(stateId);
       } catch (EmptyResultDataAccessException e){
           throw new EntityNotFound("State code % not found" + stateId);
       } catch (DataIntegrityViolationException e) {
           throw new EntityInUse("State code % could be not removed," + stateId);
       }
   }
}