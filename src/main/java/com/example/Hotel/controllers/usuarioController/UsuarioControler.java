package com.example.Hotel.controllers.usuarioController;

import com.example.Hotel.controllers.outrosControllers.outrosResponses.ClienteResponse;
import com.example.Hotel.model.outros.Cliente;
import com.example.Hotel.exceptions.EntityInUse;
import com.example.Hotel.exceptions.EntityNotFound;
import com.example.Hotel.repositorys.outrosRepository.ClienteRepository;
import com.example.Hotel.services.usuarioServices.UsuarioRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dados_do_usuario")
public class UsuarioControler {
    @Autowired
    private ClienteRepository clientRepository;
    @Autowired
    private UsuarioRegistrationService clientRegistrationService;
    @GetMapping
    public List<Cliente> list() {
        return clientRepository.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ClienteResponse> find(@PathVariable("userId") Long id) {
       final var client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFound("Cliente não encontrado"));
    if (client != null) {
        final var response = new ClienteResponse(
                client.getNameComplete(),
            client.getUsername(),
            client.getCpf(),
            client.getPhone(),
            client.getEmail(),
            client.getAddress(),
                new ClienteResponse.Nascimento(
            client.getDataNacimento()
                ));
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
    public Cliente update(@PathVariable Long clientId, @RequestBody Cliente client) {
        Cliente client1 = clientRepository.findById(clientId).get();
            BeanUtils.copyProperties(client1, client, "id");
            return clientRepository.save(client1);
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
