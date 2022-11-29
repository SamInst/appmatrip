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
    private final static Long PASSEIO_CATEGORIA = 2L;

    public PasseioController(PasseioRepository passeioRepository, PasseioService passeioRegistrationService) {
        this.passeioRepository = passeioRepository;
        this.passeioService = passeioRegistrationService;
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
        return passeioService.add(passeio);
    }

    @PutMapping("/{passeioId}") //----------------------------------------------------------------------------------
    public ResponseEntity<Passeio> update(@PathVariable Long passeioId, @RequestBody Passeio passeio) {
        Optional<Passeio> passeio1 = passeioRepository.findById(passeioId);
        if (passeio1.isPresent()) {
            BeanUtils.copyProperties(passeio1, passeio, "id", "city");
            Passeio passeioSaved = passeioService.add(passeio1.get());
            return ok(passeioSaved);
        }
        return notFound().build();
    }
    @DeleteMapping("/{passeioId}") //-----------------------------------------------------------------------------------
    public ResponseEntity<Passeio> remove(@PathVariable Long passeioId) {
        try {
            passeioService.exclude(passeioId);
            return noContent().build();
        } catch (EntityNotFound e) {
            return notFound().build();
        } catch (EntityInUse e) {
            return status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/find/passeiosByName") //------------------------------------------------------------------------------
    @ResponseBody
    public ResponseEntity<List<PasseioPrecoUnicoResponse>> passeiosByName(@RequestParam(name = "name") String name) {
        final var passeios = passeioRepository.findPasseioByNomePasseio(name.trim().toUpperCase());
        return getListResponseEntityPriceForOne(passeios);
    }

    @GetMapping("/findPasseiosByAmountPeople_CityID_StateID")
    public ResponseEntity<List<PasseioPrecoUnicoResponse>> testData2(Integer quantidadePessoa, Long city_id, Long state_id, Optional<Float> optionalPrice1, Optional<Float> optionalPrice2) {
    return passeioService.QueryTripBy_CityId_StateId_DataEntry_DataOut(quantidadePessoa, city_id, state_id, optionalPrice1,optionalPrice2);
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

