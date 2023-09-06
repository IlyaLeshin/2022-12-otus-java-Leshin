package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private String id;
    private String name;
    private String address;
    private String phones;

    public ClientDTO(Client client) {
        id = client.getId().toString();
        name = client.getName();
        address = client.getAddress().getStreet();
        phones = client.getPhones().stream().map(Phone::getNumber).collect(Collectors.joining("; "));
    }
/*
    public ClientDTO convertClientToDto(Client client) {
        return new ClientDTO(client);
    }

    public Client convertDtoToClient() {
        Long ClientId = Long.valueOf(id);
        String ClientName = name;
        Address ClientAddress = new Address(address);
        List<Phone> ClientPhones = Arrays.stream(phones.split("; "))
                .map(s -> new Phone(s, ClientId))
                .toList();
        return new Client(ClientId, ClientName, ClientAddress, ClientPhones);

    }*/
}