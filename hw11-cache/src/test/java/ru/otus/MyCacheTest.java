package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Проверка MyCache")
public class MyCacheTest {

    @Test
    @DisplayName("(Client) Проверка сброса кэша при запуске Garbage Collector.")
    void testMyCacheClientCleanedByGC() throws InterruptedException {
        HwCache<String, Client> myCacheClient = new MyCache<>();
        var limit = 100;
        for (var idx = 0; idx < limit; idx++) {
            var key = "key:" + idx;
            Client client = new Client(Long.getLong(key), "Name" + idx);
            myCacheClient.put(key, client);
        }
        int ElementsBeforeGC = myCacheClient.size();
        System.out.println(ElementsBeforeGC);
        System.gc();
        Thread.sleep(100);
        int ElementsAfterGC = myCacheClient.size();
        System.out.println(ElementsAfterGC);

        assertTrue(ElementsAfterGC < ElementsBeforeGC);
        assertEquals(0, ElementsAfterGC);
    }

    @Test
    @DisplayName("(Manager) Проверка сброса кэша при запуске Garbage Collector.")
    void testMyCacheManagerCleanedByGC() throws InterruptedException {
        HwCache<String, Manager> myCacheManager = new MyCache<>();
        var limit = 100;
        for (var idx = 0; idx < limit; idx++) {
            var key = "key:" + idx;
            Manager manager = new Manager(Long.getLong(key), "label" + idx, "param1" + idx);
            myCacheManager.put(key, manager);
        }
        int ElementsBeforeGC = myCacheManager.size();
        System.out.println(ElementsBeforeGC);
        System.gc();
        Thread.sleep(100);
        int ElementsAfterGC = myCacheManager.size();
        System.out.println(ElementsAfterGC);

        assertTrue(ElementsAfterGC < ElementsBeforeGC);
        assertEquals(0, ElementsAfterGC);
    }
}
