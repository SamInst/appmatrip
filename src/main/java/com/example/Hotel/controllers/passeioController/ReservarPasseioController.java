package com.example.Hotel.controllers.passeioController;


import com.example.Hotel.controllers.hotelController.responses.PasseioResponse;

import com.example.Hotel.exceptions.EntityDates;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.model.passeios.ReservarPasseio;
import com.example.Hotel.repositorys.passeioRepository.ReservarPasseioRepository;
import com.example.Hotel.services.passeiosServices.ReservarPasseioRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservarPasseio")
public class ReservarPasseioController {

    private final ReservarPasseioRepository reservarPasseioRepository;

    private final ReservarPasseioRegistrationService reservarPasseioRegistrationService;

    public ReservarPasseioController(ReservarPasseioRepository reservarPasseioRepository, ReservarPasseioRegistrationService reservarPasseioRegistrationService) {
        this.reservarPasseioRepository = reservarPasseioRepository;
        this.reservarPasseioRegistrationService = reservarPasseioRegistrationService;
    }

    @GetMapping
    public List<ReservarPasseio> list() {return reservarPasseioRepository.all();}
    @GetMapping("/{reservarPasseioId}")
    public ResponseEntity<PasseioResponse> find(@PathVariable("reservarPasseioId") Long id) {
        ReservarPasseio reservarPasseio = reservarPasseioRepository.perId(id);
        Float precoPasseio = reservarPasseio.getPasseio().getPasseiosPrecos().getPriceOne();
        int amountPeople = reservarPasseio.getAdults() + reservarPasseio.getChilds();
        float total = amountPeople *  precoPasseio;
        final var response = new PasseioResponse(
                reservarPasseio.getClient().getUsername(),
                reservarPasseio.getPasseio().getNomePasseio(),
                reservarPasseio.getPasseio().getDescricao(),
                new PasseioResponse.PasseiosPrecos(
                        reservarPasseio.getPasseio().getPasseiosPrecos().getPriceOne()),

                new PasseioResponse.GuiaTuristico(
                        reservarPasseio.getGuiaTuristico().getNome()
                ),
                new PasseioResponse.City(
                        reservarPasseio.getPasseio().getCidade().getName(),
                        reservarPasseio.getPasseio().getCidade().getState().getName()),
                (float) amountPeople,
                new PasseioResponse.Datas(
                        reservarPasseio.getData(),
                        reservarPasseio.getHorario()
                ),
                total

        );
        return  ResponseEntity.ok(response);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> add(@RequestBody ReservarPasseio reservarPasseio){
            try {
                LocalDate today = LocalDate.now();
                LocalDate dataEntry = reservarPasseio.getData();
                        if ((dataEntry.isBefore(today))){
                            throw new EntityDates("The date entered cannot be less than today");
                        } else {
                            reservarPasseio = reservarPasseioRegistrationService.add(reservarPasseio);
                        }
                return ResponseEntity.status(HttpStatus.CREATED).body(reservarPasseio);
            } catch (EntityNotFound e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
    }
    @PutMapping("/{reservarPasseioId}")
    public ResponseEntity<ReservarPasseio> update(@PathVariable Long reservarPasseioId, @RequestBody ReservarPasseio reservarPasseio) {
       try {
           ReservarPasseio reserva1 = reservarPasseioRepository.perId(reservarPasseioId);
        if (reserva1 != null) {
            BeanUtils.copyProperties( reserva1, reservarPasseio,"client","passeio","client", "descricaoGuia");
            return ResponseEntity.ok(reservarPasseioRegistrationService.add(reserva1));
        }
        return ResponseEntity.notFound().build();
    } catch (EntityNotFound e){
           return ResponseEntity.notFound().build();
       }
    }
    @DeleteMapping("/{reservarPasseioId}")
    public ResponseEntity<ReservarPasseio> remove(@PathVariable Long reservarPasseioId) {
        try {
            reservarPasseioRegistrationService.exclude(reservarPasseioId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFound e) {
            return ResponseEntity.notFound().build();
        } catch (EntityInUse e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}