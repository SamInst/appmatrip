package com.example.Hotel.controllers;

import com.example.Hotel.controllers.hotelController.responses.CidadeResponse;
import com.example.Hotel.model.Cidade;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.repositorys.CidadeRepository;
import com.example.Hotel.services.CidadeRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/citys") //------------------------------------------------------------------------------------------
public class CidadeController {
        private final CidadeRepository cityRepository;
        private final CidadeRegistrationService cityRegistrationService;

    public CidadeController(CidadeRepository cityRepository, CidadeRegistrationService cityRegistrationService) {
        this.cityRepository = cityRepository;
        this.cityRegistrationService = cityRegistrationService;
    }

    @GetMapping //----------------------------------------------------------------------------------------------------
        public List<Cidade> list() {
            return cityRepository.findAll();
        }

        @GetMapping("/{cityId}") //-----------------------------------------------------------------------------------
        public ResponseEntity<CidadeResponse> find(@PathVariable("cityId") Long id) {
           final var city = cityRepository.findById(id).orElseThrow(() -> new EntityNotFound("City not found"));
            if (city != null) {
                final var response = new CidadeResponse(
                        city.getName(),
                        city.getState().getName()
                );
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Cidade add(@RequestBody Cidade city) {
            return cityRegistrationService.add(city);
        }
    @PutMapping("/{cityId}") //---------------------------------------------------------------------------------------
    public ResponseEntity<Cidade> update(@PathVariable Long cityId, @RequestBody Cidade city) {
       Optional <Cidade> city1 = cityRepository.findById(cityId);
        if (city1.isPresent()) {
            BeanUtils.copyProperties(city1, city, "id");
            Cidade citySaved = cityRegistrationService.add(city1.get());
            return ResponseEntity.ok(citySaved);
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{cityId}") //------------------------------------------------------------------------------------
    public ResponseEntity<Cidade> remove(@PathVariable Long cityId) {
        try {
            cityRegistrationService.exclude(cityId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFound e) {
            return ResponseEntity.notFound().build();
        } catch (EntityInUse e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
