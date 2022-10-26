package com.example.Hotel.services.passeiosServices;

import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.model.passeios.Passeio;

import com.example.Hotel.repositorys.passeioRepository.PasseioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PasseioRegistrationService {
    @Autowired
    private PasseioRepository passeioRepository;



    public Passeio add(Passeio passeio) {
        return passeioRepository.save(passeio);
    }


    public void exclude(Long passeioId) {
       try {
           passeioRepository.deleteById(passeioId);
       } catch (EmptyResultDataAccessException e){
           throw new EntityNotFound("Passeio não encontrado" + passeioId);
       } catch (DataIntegrityViolationException e) {
           throw new EntityInUse("passeio não pode ser removido" + passeioId);
       }
   }
}