package com.example.Hotel.repositorys;

import com.example.Hotel.model.Cliente;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository {
    List<Cliente> all();
    Cliente perId(Long id);
    Cliente add(Cliente client);
    void remove(Long id);
}
