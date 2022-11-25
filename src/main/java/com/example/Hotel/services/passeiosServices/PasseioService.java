package com.example.Hotel.services.passeiosServices;

import com.example.Hotel.controllers.hotelController.responses.hotelResponses.request.hotelRequest.HotelsListInCityResponse2;
import com.example.Hotel.controllers.passeioController.passeioResponse.PasseioPrecoUnicoResponse;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.model.hotel.Hotels;
import com.example.Hotel.model.passeios.Passeio;

import com.example.Hotel.repositorys.passeioRepository.PasseioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class PasseioService {
    @Autowired
    private PasseioRepository passeioRepository;

    Float price;

    public Passeio add(Passeio passeio) {
        return passeioRepository.save(passeio);
    }

    public ResponseEntity<List<PasseioPrecoUnicoResponse>> QueryTripBy_CityId_StateId_DataEntry_DataOut( Integer quantidadePessoa, Long city_id, Long state_id){
        List<Passeio> passeioList = new ArrayList<>(passeioRepository.queryPasseioByCidade_IdAndCidade_State_Id(city_id, state_id));
        return getListResponseEntityPasseio(quantidadePessoa, passeioList);
    }

    private ResponseEntity<List<PasseioPrecoUnicoResponse>> getListResponseEntityPasseio(Integer quantidadePessoa, List<Passeio> passeio) {
        List<PasseioPrecoUnicoResponse> passeioList = new ArrayList<>();
        passeio.forEach(passeio1 ->
                {
                    price = quantidadePessoa * passeio1.getPasseiosPrecos().getPriceOne();
                    passeioList.add(new PasseioPrecoUnicoResponse(
                            new PasseioPrecoUnicoResponse.Categoria(
                            passeio1.getCategoria().getName()),
                            passeio1.getNomePasseio(),
                            passeio1.getDescricao(),
                            new PasseioPrecoUnicoResponse.PasseiosPrecos(
                                    price),
                            new PasseioPrecoUnicoResponse.City(
                                    passeio1.getCidade().getName(),
                                    passeio1.getCidade().getState().getName()),
                            passeio1.getEstrela()
                            )
                            );
                }
        );
        return  ResponseEntity.ok(passeioList);
    }


    public void exclude(Long passeioId) {
       try {
           passeioRepository.deleteById(passeioId);
       } catch (EmptyResultDataAccessException e){
           throw new EntityNotFound("Passeio não encontrado" + passeioId);
       } catch (DataIntegrityViolationException e) {
           throw new EntityInUse("passeio não pode ser removido" + passeioId);
       }
   }
}