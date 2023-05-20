package ru.otus;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.executor.DbExecutorImpl;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.crm.datasource.DriverManagerDataSource;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;
import ru.otus.crm.service.*;
import ru.otus.jdbc.mapper.*;

import javax.sql.DataSource;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Сравниваем скорость работы с cache и без него ")
@Testcontainers
class DbServiceWithCacheTest {
    private static final Logger log = LoggerFactory.getLogger(DbServiceWithCacheTest.class);
    private DBServiceClient dbServiceClient;
    private DBServiceClient dbServiceClientWithCache;

    private DBServiceManager dbServiceManager;
    private DBServiceManager dbServiceManagerWithCache;

    HwCache<String, Client> myCacheClient;

    @Container
    private final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:12");

    @BeforeEach
    public void beforeForAllTests() {
        var dataSource = new DriverManagerDataSource(postgresqlContainer.getJdbcUrl(), postgresqlContainer.getUsername(), postgresqlContainer.getPassword());
        flywayMigrations(dataSource);
        TransactionRunner transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

        myCacheClient = new MyCache<>();
        EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<>(dbExecutor, entitySQLMetaDataClient, entityClassMetaDataClient);

        dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient);
        dbServiceClientWithCache = new DbServiceClientImplWithCache(dbServiceClient, myCacheClient);

        HwCache<String, Manager> myCacheManager = new MyCache<>();
        EntityClassMetaData<Manager> entityClassMetaDataManager = new EntityClassMetaDataImpl<>(Manager.class);
        EntitySQLMetaData entitySQLMetaDataManager = new EntitySQLMetaDataImpl(entityClassMetaDataManager);
        var dataTemplateManager = new DataTemplateJdbc<>(dbExecutor, entitySQLMetaDataManager, entityClassMetaDataManager);

        dbServiceManager = new DbServiceManagerImpl(transactionRunner, dataTemplateManager);
        dbServiceManagerWithCache = new DbServiceManagerImplWithCache(dbServiceManager, myCacheManager);
    }

    @Test
    @DisplayName("(DbServiceClient) Проверка, что скорость чтения с использованием кэширования выше чем без него")
    void testDbServiceClientWithCacheFasterThenWithoutCache() {
        long startTime;
        long timerGettingClientFromDb;
        long timerGettingClientFromDbWithCache;
        List<Long> clientIds = new ArrayList<>();
        List<Client> listClientWithoutCache;
        List<Client> listClientWithCache;

        addingClientsToDb(clientIds, dbServiceClientWithCache);

        startTime = System.currentTimeMillis();
        listClientWithoutCache = gettingClients(clientIds, dbServiceClient);
        timerGettingClientFromDb = System.currentTimeMillis() - startTime;
        log.info("Чтение клиента без кэширования: {} мс.", timerGettingClientFromDb);

        startTime = System.currentTimeMillis();
        listClientWithCache = gettingClients(clientIds, dbServiceClientWithCache);
        timerGettingClientFromDbWithCache = System.currentTimeMillis() - startTime;
        log.info("Чтение клиента с кэшированием: {} мс.", timerGettingClientFromDbWithCache);

        assertEquals(listClientWithCache, listClientWithoutCache);
        assertTrue(timerGettingClientFromDbWithCache < timerGettingClientFromDb);
    }

    @Test
    @DisplayName("(DbServiceManager) Проверка, что скорость чтения с использованием кэширования выше чем без него ")
    void testDbServiceManagerWithCacheFasterThenWithout() {
        long startTime;
        long addAndGetManagerFromDbTime;
        long addAndGetManagerFromDbWithCacheTime;
        List<Long> managerIds = new ArrayList<>();
        List<Manager> listManagerWithoutCache;
        List<Manager> listManagerWithCache;

        addingManagersToDb(managerIds, dbServiceManagerWithCache);

        startTime = System.currentTimeMillis();
        listManagerWithoutCache = gettingManagers(managerIds, dbServiceManager);
        addAndGetManagerFromDbTime = System.currentTimeMillis() - startTime;
        log.info("Чтение менеджера без кэширования: {} мс.", addAndGetManagerFromDbTime);

        startTime = System.currentTimeMillis();
        listManagerWithCache = gettingManagers(managerIds, dbServiceManagerWithCache);
        addAndGetManagerFromDbWithCacheTime = System.currentTimeMillis() - startTime;
        log.info("Чтение менеджера с кэшированием: {} мс.", addAndGetManagerFromDbWithCacheTime);

        assertEquals(listManagerWithCache, listManagerWithoutCache);
        assertTrue(addAndGetManagerFromDbWithCacheTime < addAndGetManagerFromDbTime);
    }

    @Test
    @DisplayName("(DbServiceClient) Проверка, что кэш сбрасывается при запуске Garbage Collector")
    void testDbServiceClientWithCacheCleanedByGC() throws InterruptedException {

        List<Long> listClientWithCacheIds = new ArrayList<>();

        addingClientsToDb(listClientWithCacheIds, dbServiceClientWithCache);
        gettingClients(listClientWithCacheIds, dbServiceClientWithCache);
        log.info("Кэш содержит: {} элементов'.", myCacheClient.size());
        System.gc();
        Thread.sleep(100);
        log.info("Кэш содержит: {} элементов'.", myCacheClient.size());

        assertEquals(0, myCacheClient.size());
    }

    @Test
    @DisplayName("(DbServiceManager) Проверка, что кэш сбрасывается при запуске Garbage Collector")
    void testDbServiceManagerWithCacheCleanedByGC() throws InterruptedException {

        List<Long> listManagerWithCacheIds = new ArrayList<>();

        addingManagersToDb(listManagerWithCacheIds, dbServiceManager);
        gettingManagers(listManagerWithCacheIds, dbServiceManager);
        System.out.println(myCacheClient.size());
        System.gc();
        Thread.sleep(100);
        System.out.println(myCacheClient.size());

        assertEquals(0, myCacheClient.size());
    }

    private void addingClientsToDb(List<Long> listIdClient, DBServiceClient dbServiceClient) {
        for (long i = 0; i < 100; i++) {
            var client = new Client(null, "Client name " + i);
            listIdClient.add(dbServiceClient.saveClient(client).getId());
        }
    }

    private void addingManagersToDb(List<Long> listNoManagers, DBServiceManager dbServiceManager) {
        for (long i = 0; i < 100; i++) {
            var manager = new Manager();
            listNoManagers.add(dbServiceManager.saveManager(manager).getNo());
        }
    }

    private List<Client> gettingClients(List<Long> ids, DBServiceClient dbServiceClient) {
        List<Client> listClients = new ArrayList<>();
        for (Long id : ids) {
            Optional<Client> client = dbServiceClient.getClient(id);
            client.ifPresent(listClients::add);
        }
        return listClients;
    }

    private List<Manager> gettingManagers(List<Long> ids, DBServiceManager dbServiceManager) {
        List<Manager> listManagers = new ArrayList<>();
        for (Long id : ids) {
            Optional<Manager> client = dbServiceManager.getManager(id);
            client.ifPresent(listManagers::add);
        }
        return listManagers;
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }
}