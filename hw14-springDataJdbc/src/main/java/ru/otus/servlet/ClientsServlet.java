package ru.otus.servlet;

import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.model.ClientDTO;
import ru.otus.services.TemplateProcessor;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class ClientsServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_CLIENTS = "clients";
    private static final String TEMPLATE_CLIENT_NAME = "clientName";
    private static final String EMPTY_STRING = "";
    private static final String TEMPLATE_CLIENT_ADDRESS = "clientAddress";
    private static final String TEMPLATE_CLIENT_PHONE = "clientPhone";

    private final DBServiceClient dbServiceClient;
    private final TemplateProcessor templateProcessor;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        List<ClientDTO> clients = dbServiceClient.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());

        paramsMap.put(TEMPLATE_ATTR_CLIENTS, clients);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var clientName = request.getParameter(TEMPLATE_CLIENT_NAME);
        var address = request.getParameter(TEMPLATE_CLIENT_ADDRESS);
        var phones = request.getParameter(TEMPLATE_CLIENT_PHONE);

        if (clientName != null && !clientName.equals(EMPTY_STRING)) {
            Client client = new Client(clientName);

            Address clientAddress = new Address();
            clientAddress.setStreet(address);
            client.setAddress(clientAddress);

            List<Phone> clientPhoneList = Arrays.stream(phones.split(";"))
                    .map(phone -> {
                        Phone clientPhone = new Phone();
                        clientPhone.setNumber(phone);
                        return clientPhone;
                    }).toList();

            client.setPhones(clientPhoneList);

            dbServiceClient.saveClient(client);
        }
        response.sendRedirect("clients");
    }
}
