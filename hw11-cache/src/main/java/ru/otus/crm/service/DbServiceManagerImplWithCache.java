package ru.otus.crm.service;

import ru.otus.cachehw.HwCache;
import ru.otus.crm.model.Manager;

import java.util.List;
import java.util.Optional;

public class DbServiceManagerImplWithCache implements DBServiceManager {

    private final DBServiceManager serviceManager;
    private final HwCache<String, Manager> cache;

    public DbServiceManagerImplWithCache(DBServiceManager dbServiceManager, HwCache<String, Manager> hwCache) {
        this.serviceManager = dbServiceManager;
        this.cache = hwCache;
    }

    @Override
    public Manager saveManager(Manager manager) {
        return addToCache(serviceManager.saveManager(manager));
    }

    @Override
    public Optional<Manager> getManager(long no) {
        String stringId = getKeyForCache(no);
        return Optional.ofNullable(cache.get(stringId))
                .or(() -> {
                            Optional<Manager> getManagerByNo = serviceManager.getManager(no);
                            getManagerByNo.ifPresent(this::addToCache);
                            return getManagerByNo;
                        }
                );
    }

    @Override
    public List<Manager> findAll() {
        return serviceManager.findAll();
    }

    private Manager addToCache(Manager manager) {
        Manager clonedManager = manager.clone();
        String stringId = String.valueOf(clonedManager.getNo());
        cache.put(stringId, clonedManager);
        return clonedManager;
    }

    private String getKeyForCache(long no){
        return String.valueOf(no);
    }
}