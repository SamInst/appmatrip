package com.example.Hotel.repositorys.outrosRepository;

import com.example.Hotel.model.outros.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository <Estado, Long> {

}
