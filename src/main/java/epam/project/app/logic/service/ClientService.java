package epam.project.app.logic.service;
import epam.project.app.logic.entity.dto.ClientRegistrationDto;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.exception.ClientException;
import epam.project.app.logic.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public Client registration(ClientRegistrationDto dto) {
        return clientRepository.insertClient(dto).orElseThrow(() -> new ClientException("cannot register new Client"));
    }

    public Client getClientById(Long id) {
        return clientRepository.getClientById(id).orElseThrow(() -> new ClientException("cannot get client"));
    }

    public List<Client> getAllClients() {
        List<Client> clients = clientRepository.getAllClients();
        return clients;
    }

    public Client deleteClientById(Long id) {
        return clientRepository.deleteClientById(id).orElseThrow(() -> new ClientException("cannot delete client"));
    }

    public List<Client> searchClientsByParameters(Map<String, String> parameters) {
        return clientRepository.getClientsByParameter(parameters);
    }
}
