package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

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
}