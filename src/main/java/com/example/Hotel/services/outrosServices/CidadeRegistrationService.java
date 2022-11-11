package com.example.Hotel.services.outrosServices;

import com.example.Hotel.model.outros.Cidade;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.repositorys.outrosRepository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CidadeRegistrationService {
    @Autowired
    private CidadeRepository cityRepository;

    public Cidade add(Cidade city) {
        return cityRepository.save(city);
    }
    public void exclude(Long cityId) {
       try {
           cityRepository.deleteById(cityId);
       } catch (EmptyResultDataAccessException e){
           throw new EntityNotFound("City code % not found" + cityId);
       } catch (DataIntegrityViolationException e) {
           throw new EntityInUse("City code % could be not removed," + cityId);
       }
   }
}