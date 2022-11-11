package com.example.Hotel.controllers.outrosControllers;

import com.example.Hotel.model.outros.Estado;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.repositorys.outrosRepository.EstadoRepository;
import com.example.Hotel.services.outrosServices.EstadoRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/states")
public class EstadoController {
    @Autowired
    private EstadoRepository stateRepository;
    @Autowired
    private EstadoRegistrationService stateRegistrationService;

    @GetMapping
    public List<Estado> list() {
        return stateRepository.findAll();
    }

    @GetMapping("/{stateId}")
    public ResponseEntity<Estado> find(@PathVariable("stateId") Long id) {
        Optional<Estado> state = stateRepository.findById(id);
        if (state != null) {
            return ResponseEntity.ok(state.get());
        } else {
//        return ResponseEntity.status(HttpStatus.FOUND).build();
            return ResponseEntity.notFound().build();
        }
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Estado add(@RequestBody Estado state) {
        return stateRegistrationService.add(state);
    }

//    @PutMapping("/{stateId}")
//    public ResponseEntity<Estado> update(@PathVariable Long stateId, @RequestBody Estado state) {
//       Optional<Estado> state1 = stateRepository.findById(stateId);
//        if (state1 != null) {
//            BeanUtils.copyProperties(state, state1, "id");
//            state1 = stateRegistrationService.(state1.);
//            return ResponseEntity.ok(state1.get());
//        }
//        return ResponseEntity.notFound().build();
//    }
    @DeleteMapping("/{stateId}")
    public ResponseEntity<Estado> remove(@PathVariable Long stateId) {
        try {
            stateRegistrationService.exclude(stateId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFound e) {
            return ResponseEntity.notFound().build();
        } catch (EntityInUse e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
