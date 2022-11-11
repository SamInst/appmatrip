package com.example.Hotel.controllers.passeioController;


import com.example.Hotel.controllers.passeioController.passeioResponse.PasseioPrecoUnicoResponse;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.model.passeios.Passeio;
import com.example.Hotel.repositorys.passeioRepository.PasseioRepository;
import com.example.Hotel.services.passeiosServices.PasseioRegistrationService;
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

    private final PasseioRegistrationService passeioRegistrationService;
    private final static Long PASSEIO_CATEGORIA = 2L;

    public PasseioController(PasseioRepository passeioRepository, PasseioRegistrationService passeioRegistrationService) {
        this.passeioRepository = passeioRepository;
        this.passeioRegistrationService = passeioRegistrationService;
    }

    @GetMapping
    public List<Passeio> list() {
        return passeioRepository.findAll();
    }

    @GetMapping("/{passeioId}") //----------------------------------------------------------------------------------
    public ResponseEntity<PasseioPrecoUnicoResponse> find(@PathVariable("passeioId") Long id) {
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping //-----------------------------------------------------------------------------------------------
    public Passeio add(@RequestBody Passeio passeio) {
        return passeioRegistrationService.add(passeio);
    }

    @PutMapping("/{passeioId}") //----------------------------------------------------------------------------------
    public ResponseEntity<Passeio> update(@PathVariable Long passeioId, @RequestBody Passeio passeio) {
        Optional<Passeio> passeio1 = passeioRepository.findById(passeioId);
        if (passeio1.isPresent()) {
            BeanUtils.copyProperties(passeio1, passeio, "id", "city");
            Passeio passeioSaved = passeioRegistrationService.add(passeio1.get());
            return ok(passeioSaved);
        }
        return notFound().build();
    }
    @DeleteMapping("/{passeioId}") //-----------------------------------------------------------------------------------
    public ResponseEntity<Passeio> remove(@PathVariable Long passeioId) {
        try {
            passeioRegistrationService.exclude(passeioId);
            return noContent().build();
        } catch (EntityNotFound e) {
            return notFound().build();
        } catch (EntityInUse e) {
            return status(HttpStatus.CONFLICT).build();
        }
    }
    @GetMapping("/findPasseiosByStateId") //-------------------------------------------------------------------------------
    public ResponseEntity<List<PasseioPrecoUnicoResponse>> findPasseiosByStateId(@RequestParam (name = "state_id") Long state_id) {
        final var passeios = passeioRepository.queryPasseioByCidade_State_IdAndCategoria_Id(state_id, PASSEIO_CATEGORIA);
        return getListResponseEntityPriceForOne(passeios);
    }

    @GetMapping("/find/passeiosByName") //------------------------------------------------------------------------------
    @ResponseBody
    public ResponseEntity<List<PasseioPrecoUnicoResponse>> passeiosByName(@RequestParam(name = "name") String name) {
        final var passeios = passeioRepository.findPasseioByNomePasseio(name.trim().toUpperCase());
        return getListResponseEntityPriceForOne(passeios);
    }
    @GetMapping("/findpasseiosByCityName") //---------------------------------------------------------------------------
    @ResponseBody
    public ResponseEntity<List<PasseioPrecoUnicoResponse>> findPasseiosByCityName(@RequestParam(name = "name") String name) {
        final var passeios = passeioRepository.findPasseioByCidade_Name(name.trim().toUpperCase());
        return getListResponseEntityPriceForOne(passeios);
    }
    @GetMapping("/findpasseiosByCityId") //-------------------------------------------------------------------------------
    public ResponseEntity<List<PasseioPrecoUnicoResponse>> findPasseiosInCity(Long id) {
        final var passeios = passeioRepository.findPasseioByCidade_Id(id);
        return getListResponseEntityPriceForOne(passeios);
    }
    @GetMapping("/findPasseioByPrice") //-----------------------------------------------------------------------------
    public ResponseEntity<List<PasseioPrecoUnicoResponse>> findByPriceBetween(Float price1, Float price2) {
        final var passeios = passeioRepository.queryByPasseiosPrecos_PriceOneBetween(price1, price2);
        return getListResponseEntityPriceForOne(passeios);
    }
    @GetMapping("/findPasseioByPriceBetweenAndCityId") //--------------------------------------------------------------------
    public ResponseEntity<List<PasseioPrecoUnicoResponse>> findByPriceBetween(Float price1, Float price2, Long id) {
        final var passeios = passeioRepository.queryByPasseiosPrecos_PriceOneBetweenAndCidade_Id(price1, price2, id);
        return getListResponseEntityPriceForOne(passeios);
    }
    @GetMapping("/findPasseioByPriceBetweenAndCityName") //--------------------------------------------------------------------
    public ResponseEntity<List<PasseioPrecoUnicoResponse>> findByPriceBetweenAndCityName(Float price1, Float price2, String name) {
        final var passeios = passeioRepository.queryByPasseiosPrecos_PriceOneBetweenAndCidade_Name(price1, price2, name.trim().toUpperCase());
        return getListResponseEntityPriceForOne(passeios);
    }

    @GetMapping("/findPasseiosByDestaques") //--------------------------------------------------------------------------
    public ResponseEntity<List<PasseioPrecoUnicoResponse>> findPasseiosByDestaques(Integer destaque) {
        final var passeios = passeioRepository.queryPasseioByDestaque(destaque);
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
        return  ResponseEntity.ok(passeioPrecoUnicoResponses);
    }


    }

