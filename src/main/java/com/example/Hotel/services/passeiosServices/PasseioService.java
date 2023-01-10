package com.example.Hotel.services.passeiosServices;

import com.example.Hotel.controllers.passeioController.passeioResponse.PasseioPrecoUnicoResponse;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.model.passeios.Passeio;
import com.example.Hotel.repositorys.passeioRepository.PasseioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@Service
public class PasseioService {
    @Autowired
    private PasseioRepository passeioRepository;
    Float price;

    public Passeio add(Passeio passeio) {
        return passeioRepository.save(passeio);
    }

    public ResponseEntity<List<PasseioPrecoUnicoResponse>> QueryTripBy_CityId_StateId_DataEntry_DataOut(Integer quantidadePessoa, Long city_id, Long state_id, Optional<Float> optionalPrice1, Optional<Float> optionalPrice2) {
        List<Passeio> passeioList = new ArrayList<>(passeioRepository.queryPasseioByCidade_IdAndCidade_State_IdAndPasseiosPrecos_PriceOneBetween(city_id, state_id, optionalPrice1, optionalPrice2));
        return getListResponseEntityPasseio(quantidadePessoa, passeioList);
    }

    private ResponseEntity<List<PasseioPrecoUnicoResponse>> getListResponseEntityPasseio(Integer quantidadePessoa, List<Passeio> passeio) {
        List<PasseioPrecoUnicoResponse> passeioList = new ArrayList<>();
        passeio.forEach(passeio1 ->
                {
                    price = (passeio1.getPasseiosPrecos().getPriceOne() * quantidadePessoa);
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
        return ResponseEntity.ok(passeioList);
    }

    public ResponseEntity<List<PasseioPrecoUnicoResponse>> PasseiosByDestaques(Integer destaque, Integer quantidadePessoa) {
        final var passeiosList = passeioRepository.queryPasseioByDestaque(destaque);
        return getListResponseEntityPasseio(destaque, passeiosList);
    }

    public void exclude(Long passeioId) {
        try {
            passeioRepository.deleteById(passeioId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFound("Passeio não encontrado" + passeioId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUse("passeio não pode ser removido" + passeioId);
        }
    }

    public ResponseEntity<Passeio> removeById(Long passeioId) {
        try {
            exclude(passeioId);
            return noContent().build();
        } catch (EntityNotFound e) {
            return notFound().build();
        } catch (EntityInUse e) {
            return status(HttpStatus.CONFLICT).build();
        }
    }

    public ResponseEntity<List<PasseioPrecoUnicoResponse>> passeiosByName(String name) {
        final var passeios = passeioRepository.findPasseioByNomePasseio(name.trim().toUpperCase());
        return getListResponseEntityPriceForOne(passeios);
    }

    private ResponseEntity<List<PasseioPrecoUnicoResponse>> getListResponseEntityPriceForOne(List<Passeio> passeios) {
        List<PasseioPrecoUnicoResponse> passeioPrecoUnicoResponses = new ArrayList<>();
        passeios.forEach(
                passeio -> passeioPrecoUnicoResponses.add(new PasseioPrecoUnicoResponse(
                        new PasseioPrecoUnicoResponse.Categoria(
                                passeio.getCategoria().getName()),
                        passeio.getNomePasseio(),
                        passeio.getDescricao(),
                        new PasseioPrecoUnicoResponse.PasseiosPrecos(
                                passeio.getPasseiosPrecos().getPriceOne()),
                        new PasseioPrecoUnicoResponse.City(
                                passeio.getCidade().getName(),
                                passeio.getCidade().getState().getName()
                        ),
                        passeio.getEstrela()
                )));
        return ResponseEntity.ok(passeioPrecoUnicoResponses);
    }

    public ResponseEntity<Passeio> updateById(Long passeioId, Passeio passeio) {
        Optional<Passeio> passeio1 = passeioRepository.findById(passeioId);
        if (passeio1.isPresent()) {
            BeanUtils.copyProperties(passeio1, passeio, "id", "city");
            Passeio passeioSaved = add(passeio1.get());
            return ok(passeioSaved);
        }
        return notFound().build();
    }

    public ResponseEntity<PasseioPrecoUnicoResponse> findById(Long id) {
        final var passeio = passeioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Passeio não encontrado"));
        if (passeio != null) {
            final var response = new PasseioPrecoUnicoResponse(
                    new PasseioPrecoUnicoResponse.Categoria(
                            passeio.getCategoria().getName()
                    ),
                    passeio.getNomePasseio(),
                    passeio.getDescricao(),
                    new PasseioPrecoUnicoResponse.PasseiosPrecos(
                            passeio.getPasseiosPrecos().getPriceOne()),
                    new PasseioPrecoUnicoResponse.City(passeio.getCidade().getName(),
                            passeio.getCidade().getState().getName()),
                    passeio.getEstrela()
            );
            return ok(response);
        } else {
            return notFound().build();
        }
    }
}