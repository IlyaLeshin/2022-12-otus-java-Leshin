package ru.otus.crm.service;

import ru.otus.cachehw.HwCache;
import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class DbServiceClientImplWithCache implements DBServiceClient {

    private final DBServiceClient serviceClient;
    private final HwCache<String, Client> cache;

    public DbServiceClientImplWithCache(DBServiceClient dbServiceClient, HwCache<String, Client> hwCache) {
        this.serviceClient = dbServiceClient;
        this.cache = hwCache;
    }

    @Override
    public Client saveClient(Client client) {
        return addToCache(serviceClient.saveClient(client));
    }

    @Override
    public Optional<Client> getClient(long id) {
        String stringId = String.valueOf(id);
        return Optional.ofNullable(cache.get(stringId)).
                or(() -> {
                            Optional<Client> getClientById = serviceClient.getClient(id);
                            getClientById.ifPresent(this::addToCache);
                            return getClientById;
                        }
                );
    }

    @Override
    public List<Client> findAll() {
        return serviceClient.findAll();
    }

    private Client addToCache(Client client) {
        Client ClonedClient = client.clone();
        String stringId = String.valueOf(ClonedClient.getId());
        cache.put(stringId, ClonedClient);
        return ClonedClient;
    }
}