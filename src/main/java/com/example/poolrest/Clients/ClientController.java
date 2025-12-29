package com.example.poolrest.Clients;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v0/pool/client")
public class ClientController {
    public static final Logger log = LoggerFactory.getLogger(ClientController.class);
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AllClientDto>> getAllClient() {
        log.info("called getAllClient");
        return ResponseEntity.status(HttpStatus.OK)
                .body(clientService.getAllClients());
    }
    @GetMapping("/get/{clientId}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable("clientId") Long id) {
        log.info("called getClientById id = {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(clientService.getClientById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<ClientDto> createClient(@RequestBody @Valid ClientDto client) {
        log.info("called createClient data = {}", client.toString());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.createClient(client));
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable("clientId") Long id) {
        log.info("called deleteClient id = {}", id);
        clientService.deleteClient(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
