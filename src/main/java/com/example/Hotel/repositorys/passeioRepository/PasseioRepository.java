package com.example.Hotel.repositorys.passeioRepository;

import com.example.Hotel.model.passeios.Passeio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasseioRepository extends JpaRepository<Passeio, Long> {

    @Query(value = "select u from Passeio u where upper(trim(u.nomePasseio)) like %?1%")
    List<Passeio> findPasseioByNomePasseio (String name);

    List<Passeio>queryPasseioByCidade_IdAndCidade_State_IdAndPasseiosPrecos_PriceOneBetween(Long city_id, Long state_id, Optional<Float> price1, Optional<Float> price2);

    @Query(value = "select u from Passeio u where u.Destaque = 1 ")
    List<Passeio> queryPasseioByDestaque (Integer destaque);
}
