package com.example.Hotel.controllers.usuarioController;

import com.example.Hotel.controllers.hotelController.responses.ClienteResponse;
import com.example.Hotel.model.Cliente;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.repositorys.ClienteRepository;
import com.example.Hotel.services.usuarioServices.UsuarioRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class UsuarioControler {
    @Autowired
    private ClienteRepository clientRepository;

    @Autowired
    private UsuarioRegistrationService clientRegistrationService;

    @GetMapping
    public List<Cliente> list() {
        return clientRepository.all();
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClienteResponse> find(@PathVariable("clientId") Long id) {
        Cliente client = clientRepository.perId(id);
    if (client != null) {
        final var response = new ClienteResponse(
            client.getUsername(),
            client.getCpf(),
            client.getPhone(),
            client.getEmail(),
            client.getAddress());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Cliente add(@RequestBody Cliente client) {
        return clientRegistrationService.add(client);
    }
    @PutMapping("/{clientId}")
    public ResponseEntity<Cliente> update(@PathVariable Long clientId, @RequestBody Cliente client) {
        Cliente client1 = clientRepository.perId(clientId);

        if (client1 != null) {
            BeanUtils.copyProperties(client1, client, "id"); // for a lot of data in kitchen

            client1 = clientRegistrationService.add(client1);
            return ResponseEntity.ok(client1);
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{clientId}")
    public ResponseEntity<Cliente> remove(@PathVariable Long clientId) {
        try {
            clientRegistrationService.exclude(clientId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFound e) {
            return ResponseEntity.notFound().build();
        } catch (EntityInUse e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
