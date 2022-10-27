package com.example.Hotel.repositorys;

import com.example.Hotel.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository <Estado, Long> {

}
