package ru.otus.cachehw;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    //Надо реализовать эти методы
    private static final Logger log = LoggerFactory.getLogger(MyCache.class);
    private final Map<K, V> myCache = new WeakHashMap<>();
    private final List<HwListener<K, V>> hwListeners = new ArrayList<>();

    public MyCache() {
    }

    @Override
    public void put(K key, V value) {
        myCache.put(key, value);
        notifyAllListeners(key, value, Action.PUT);
    }

    @Override
    public void remove(K key) {
        V value = myCache.remove(key);
        notifyAllListeners(key, value, Action.REMOVE);
    }

    @Override
    public V get(K key) {
        V value = myCache.get(key);
        notifyAllListeners(key, value, Action.GET);
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        hwListeners.add(listener);
        log.debug("Listener [{}] added", listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        hwListeners.remove(listener);
        log.debug("Listener [{}] removed", listener);
    }

    @Override
    public int size(){
        return myCache.size();
    }

    private void notifyAllListeners(K key, V value, Action action) {
        hwListeners.forEach(listener ->
                {
                    try {
                        listener.notify(key, value, action.name());
                    } catch (Exception e) {
                        log.error("Listener error", e);
                    }
                }
        );
    }

    private enum Action {
        PUT, REMOVE, GET
    }
}
