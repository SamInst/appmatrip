package com.example.Hotel.repositorys.outrosRepository;

import com.example.Hotel.model.outros.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository <Cliente, Long> {

}
