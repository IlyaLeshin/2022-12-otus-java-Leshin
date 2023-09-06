package ru.otus.model;

import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.util.stream.Collectors;

public class ClientDTO {
    private final String id;
    private final String name;
    private final String address;
    private final String phones;

    public ClientDTO(Client client) {
        id = client.getId().toString();
        name = client.getName();
        address = client.getAddress().getStreet();
        phones = client.getPhones().stream().map(Phone::getNumber).collect(Collectors.joining("; "));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhones() {
        return phones;
    }
}