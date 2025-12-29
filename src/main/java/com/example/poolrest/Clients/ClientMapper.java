package com.example.poolrest.Clients;

import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public ClientDto toDomain(Client client) {
        return new ClientDto(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPhone()
        );
    }

    public Client toEntity(ClientDto clientDto) {
        return new Client(
                clientDto.id(),
                clientDto.name(),
                clientDto.email(),
                clientDto.phone()
        );
    }
    public AllClientDto toAllDomain(Client client){
        return new AllClientDto(
                client.getId(),
                client.getName()
        );
    }
}
