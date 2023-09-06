package ru.otus.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.dto.ClientDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final DBServiceClient dbServiceClient;


    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        var client = convertDtoToClient(clientDTO);
        dbServiceClient.saveClient(client);
        return clientDTO;

    }

    @Override
    public ClientDTO getClient(long id) {
        Client client = dbServiceClient.getClient(id).orElseThrow(RuntimeException::new);

        return new ClientDTO(client);
    }

    @Override
    public List<ClientDTO> findAll() {

        var clientList = dbServiceClient.findAll();

        return clientList.stream()
                .map(this::convertClientToDto)
                .toList();
    }


    private ClientDTO convertClientToDto(Client client) {
        return new ClientDTO(client);
    }


    private Client convertDtoToClient(ClientDTO clientDto) {
               String name = clientDto.getName();
        Address address = new Address(null, clientDto.getAddress());
        List<Phone> phones = Arrays.stream(clientDto.getPhones().split(";"))
                .map(phone -> new Phone(null, phone, null))
                .collect(Collectors.toList());

        return new Client(name, address, phones);
    }
}
