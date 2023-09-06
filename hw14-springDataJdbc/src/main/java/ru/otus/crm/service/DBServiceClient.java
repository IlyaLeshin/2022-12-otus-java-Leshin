package ru.otus.crm.service;

import ru.otus.crm.model.Client;

import java.util.Optional;
import java.util.Set;

public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    Set<Client> findAll();
}
