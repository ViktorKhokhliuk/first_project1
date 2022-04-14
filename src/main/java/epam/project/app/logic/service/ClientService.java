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
        return clientRepository.insertClient(dto).orElseThrow(() -> new ClientException("user with this login already exists"));
    }

    public List<Client> getAllClients(int page) {
        int index = (page - 1) * 5;
        return clientRepository.getAllClients(index);
    }

    public boolean deleteClientById(Long id) {
        if (clientRepository.deleteClientById(id))
            return true;
        throw new ClientException("Cannot delete client");
    }

    public List<Client> searchClientsByParameters(Map<String, String> parameters) {
        return clientRepository.getClientsByParameter(parameters);
    }

    public double getCountOfPage() {
        double countOfPage = clientRepository.getCountOfPage();
        return Math.ceil(countOfPage / 5);
    }
}
