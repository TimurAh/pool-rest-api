package com.example.poolrest.Clients;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    public static final Logger log = LoggerFactory.getLogger(ClientService.class);
    @Autowired
    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }


    public List<AllClientDto> getAllClients() {
        return clientRepository.findAll().stream().map(clientMapper::toAllDomain).toList();
    }

    public ClientDto createClient(ClientDto client) {

        if (client.id() != null) {
            throw new IllegalArgumentException("Id must be empty");
        }

        var entityToSave =clientMapper.toEntity(client);
        if (clientRepository.findByEmail(entityToSave.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already taken");
        }
        var savedEntity = clientRepository.save(entityToSave);
        log.info("Client with data={} successfully created",client.toString());
        return clientMapper.toDomain(savedEntity);
    }

    public void deleteClient(Long id) {
        if (clientRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Client not find by id = %s".formatted(id));
        }
        clientRepository.deleteById(id);
        log.info("Client with id={} successfully deleted",id);
    }

    public ClientDto getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found client by id = %s".formatted(id)
                ));
        return clientMapper.toDomain(client);
    }
}
