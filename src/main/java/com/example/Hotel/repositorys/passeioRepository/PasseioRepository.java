package com.example.Hotel.repositorys.passeioRepository;

import com.example.Hotel.model.hotel.Hotels;
import com.example.Hotel.model.passeios.Passeio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasseioRepository extends JpaRepository<Passeio, Long> {


    @Query(value = "select u from Passeio u where upper(trim(u.nomePasseio)) like %?1%")
    List<Passeio> findPasseioByNomePasseio (String name);

    List<Passeio>queryPasseioByCidade_IdAndCidade_State_Id(Long city_id, Long state_id);

    @Query(value = "select u from Passeio u join u.cidade  where upper(trim(u.cidade.name)) like %?1%")




    List<Passeio> queryByPasseiosPrecos_PriceOneBetweenAndCidade_Id (Float price1, Float price2, Long Id);

    @Query(value = "select u from Passeio u join u.cidade  where u.passeiosPrecos.priceOne between :price1 and :price2 and upper(trim(u.cidade.name)) like %:name%")
    List<Passeio> queryByPasseiosPrecos_PriceOneBetweenAndCidade_Name(Float price1, Float price2, String name);

    List<Passeio> queryPasseioByCidade_State_IdAndCategoria_Id(Long state_id, Long categoria);

    @Query(value = "select u from Passeio u where u.Destaque = 1 ")
    List<Passeio> queryPasseioByDestaque (Integer destaque);
}
