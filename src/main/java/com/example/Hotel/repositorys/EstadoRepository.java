package com.example.Hotel.repositorys;

import com.example.Hotel.model.Estado;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstadoRepository {
    List<Estado> all();
    Estado perId(Long id);
    Estado add(Estado state);
    void remove(Long id);
}
