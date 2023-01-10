package com.example.Hotel.controllers.passeioController;


import com.example.Hotel.controllers.passeioController.passeioResponse.PasseioPrecoUnicoResponse;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.model.passeios.Passeio;
import com.example.Hotel.repositorys.passeioRepository.PasseioRepository;
import com.example.Hotel.services.passeiosServices.PasseioService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/passeios")
public class PasseioController {
    private final PasseioRepository passeioRepository;
    private final PasseioService passeioService;
//    private final static Long PASSEIO_CATEGORIA = 2L;

    public PasseioController(PasseioRepository passeioRepository, PasseioService passeioRegistrationService) {
        this.passeioRepository = passeioRepository;
        this.passeioService = passeioRegistrationService;
    }

    @GetMapping
    public List<Passeio> list() {
        return passeioRepository.findAll();
    }

    @GetMapping("/{passeioId}") //------------------------------------------------------------------------------------
    public ResponseEntity<PasseioPrecoUnicoResponse> find(@PathVariable("passeioId") Long id) {
        return passeioService.findById(id);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping //---------------------------------------------------------------------------------------------------
    public Passeio add(@RequestBody Passeio passeio) {
        return passeioService.add(passeio);
    }

    @PutMapping("/{passeioId}") //------------------------------------------------------------------------------------
    public ResponseEntity<Passeio> update(@PathVariable Long passeioId, @RequestBody Passeio passeio) {
        return passeioService.updateById(passeioId, passeio);
    }
    @DeleteMapping("/{passeioId}") //---------------------------------------------------------------------------------
    public ResponseEntity<Passeio> remove(@PathVariable Long passeioId) {
        return passeioService.removeById(passeioId);
    }

    @GetMapping("/find/passeiosByName") //----------------------------------------------------------------------------
    @ResponseBody
    public ResponseEntity<List<PasseioPrecoUnicoResponse>> passeiosByName(@RequestParam(name = "name") String name) {
        return passeioService.passeiosByName(name);
    }

    @GetMapping("/findPasseiosByAmountPeople_CityID_StateID") //------------------------------------------------------
    public ResponseEntity<List<PasseioPrecoUnicoResponse>> testData2(Integer quantidadePessoa, Long city_id, Long state_id, Optional<Float> optionalPrice1, Optional<Float> optionalPrice2) {
    return passeioService.QueryTripBy_CityId_StateId_DataEntry_DataOut(quantidadePessoa, city_id, state_id, optionalPrice1,optionalPrice2);
    }

    @GetMapping("/findPasseiosByDestaques") //------------------------------------------------------------------------
    public ResponseEntity<List<PasseioPrecoUnicoResponse>> findPasseiosByDestaques(Integer destaque, Integer quantidadePessoa) {
        return passeioService.PasseiosByDestaques(destaque, quantidadePessoa);
    }
    }