package com.example.Hotel.services.usuarioServices;

import com.example.Hotel.model.Cliente;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.repositorys.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioRegistrationService {
    @Autowired
    private ClienteRepository clientRepository;

    public Cliente add(Cliente client) {
        return clientRepository.add(client);
    }
    public void exclude(Long clientId) {
       try {
           clientRepository.remove(clientId);
       } catch (EmptyResultDataAccessException e){
           throw new EntityNotFound("Client code % not found" + clientId);
       } catch (DataIntegrityViolationException e) {
           throw new EntityInUse("Client code % could be not removed," + clientId);
       }
   }
}