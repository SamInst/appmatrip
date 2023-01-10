package com.example.Hotel.services.passeiosServices;

import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.model.passeios.ReservarPasseio;
import com.example.Hotel.repositorys.passeioRepository.ReservarPasseioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ReservarPasseioRegistrationService {
    private  final ReservarPasseioRepository reservarPasseioRepository;

    public ReservarPasseioRegistrationService(ReservarPasseioRepository reservarPasseioRepository) {
        this.reservarPasseioRepository = reservarPasseioRepository;
    }

    public ReservarPasseio add(ReservarPasseio reservarPasseio) {return reservarPasseioRepository.add(reservarPasseio);}
    public void exclude(Long passeiosId) {
       try {
           reservarPasseioRepository.remove(passeiosId);
       } catch (EmptyResultDataAccessException e){
           throw new EntityNotFound("passeio não encontrado" + passeiosId);
       } catch (DataIntegrityViolationException e) {
           throw new EntityInUse("passeio não foi removido," + passeiosId);
       }
   }
}