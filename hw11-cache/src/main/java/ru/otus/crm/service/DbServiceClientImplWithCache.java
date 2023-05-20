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
        String stringId = getKeyForCache(id);
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
        Client clonedClient = client.clone();
        String stringId = String.valueOf(clonedClient.getId());
        cache.put(stringId, clonedClient);
        return clonedClient;
    }
    private String getKeyForCache(long id){
        return String.valueOf(id);
    }
}