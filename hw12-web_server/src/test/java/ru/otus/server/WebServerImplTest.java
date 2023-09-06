package ru.otus.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.*;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.AuthService;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static ru.otus.server.utils.WebServerHelper.*;

@DisplayName("Тест сервера должен ")
class WebServerImplTest {

    private static final int WEB_SERVER_PORT = 8989;
    private static final String WEB_SERVER_URL = "http://localhost:" + WEB_SERVER_PORT + "/";
    private static final String LOGIN_URL = "login";
    private static final String API_CLIENT_URL = "api/client";

    private static final String DEFAULT_ADMINISTRATOR_LOGIN = "admin";
    private static final String DEFAULT_ADMINISTRATOR_PASSWORD = "admin";
    private static final String INCORRECT_ADMINISTRATOR_LOGIN = "BadAdmin";

    private static final long DEFAULT_CLIENT_ID = 1L;
    private static final Client DEFAULT_CLIENT = new Client(null, "Vasya", new Address(null, "AnyStreet"), List.of(new Phone(null, "13-555-22"),
            new Phone(null, "14-666-333")));
    private static Gson gson;
    private static WebServer webServer;
    private static HttpClient http;

    @BeforeAll
    static void setUp() throws Exception {
        http = HttpClient.newHttpClient();

        TemplateProcessor templateProcessor = mock(TemplateProcessor.class);
        AuthService authService = mock(AuthService.class);
        DBServiceClient dbServiceClient = mock(DBServiceClient.class);

        given(authService.authenticate(DEFAULT_ADMINISTRATOR_LOGIN, DEFAULT_ADMINISTRATOR_PASSWORD)).willReturn(true);
        given(authService.authenticate(INCORRECT_ADMINISTRATOR_LOGIN, DEFAULT_ADMINISTRATOR_PASSWORD)).willReturn(false);
        given(dbServiceClient.getClient(DEFAULT_CLIENT_ID)).willReturn(Optional.of(DEFAULT_CLIENT));

        gson = new GsonBuilder().serializeNulls().create();
        webServer = new WebServerImpl(WEB_SERVER_PORT, authService, dbServiceClient, gson, templateProcessor);
        webServer.start();
    }

    @AfterAll
    static void tearDown() throws Exception {
        webServer.stop();
    }

    @DisplayName("возвращать 302 при запросе клиентов если не выполнен вход ")
    @Test
    void shouldReturnForbiddenStatusForUserRequestWhenUnauthorized() throws Exception {
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create(buildUrl(WEB_SERVER_URL, API_CLIENT_URL)))
                .build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_MOVED_TEMP);
    }

    @DisplayName("возвращать ID сессии при выполнении входа с верными данными")
    @Test
    void shouldReturnJSessionIdWhenLoggingInWithCorrectData() throws Exception {
        HttpCookie jSessionIdCookie = login(buildUrl(WEB_SERVER_URL, LOGIN_URL), DEFAULT_ADMINISTRATOR_LOGIN, DEFAULT_ADMINISTRATOR_PASSWORD);
        assertThat(jSessionIdCookie).isNotNull();
    }

    @DisplayName("не возвращать ID сессии при выполнении входа если данные входа не верны")
    @Test
    void shouldNotReturnJSessionIdWhenLoggingInWithIncorrectData() throws Exception {
        HttpCookie jSessionIdCookie = login(buildUrl(WEB_SERVER_URL, LOGIN_URL), INCORRECT_ADMINISTRATOR_LOGIN, DEFAULT_ADMINISTRATOR_PASSWORD);
        assertThat(jSessionIdCookie).isNull();
    }

    @DisplayName("возвращать корректные данные при запросе клиента если вход выполнен")
    @Test
    void shouldReturnCorrectUserWhenAuthorized() throws Exception {
        HttpCookie jSessionIdCookie = login(buildUrl(WEB_SERVER_URL, LOGIN_URL), DEFAULT_ADMINISTRATOR_LOGIN, DEFAULT_ADMINISTRATOR_PASSWORD);
        assertThat(jSessionIdCookie).isNotNull();

        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create(buildUrl(WEB_SERVER_URL, API_CLIENT_URL, String.valueOf(DEFAULT_CLIENT_ID))))
                .setHeader(COOKIE_HEADER, String.format("%s=%s", jSessionIdCookie.getName(), jSessionIdCookie.getValue()))
                .build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        assertThat(response.body()).isEqualTo(gson.toJson(DEFAULT_CLIENT.toString()));
    }
}