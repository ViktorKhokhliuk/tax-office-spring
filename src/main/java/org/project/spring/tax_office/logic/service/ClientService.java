package org.project.spring.tax_office.logic.service;

import lombok.RequiredArgsConstructor;
import org.project.spring.tax_office.logic.entity.dto.ClientRegistrationDto;
import org.project.spring.tax_office.logic.entity.dto.ClientSearchDto;
import org.project.spring.tax_office.logic.entity.user.Client;
import org.project.spring.tax_office.logic.entity.user.User;
import org.project.spring.tax_office.logic.repository.client.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public Client registration(ClientRegistrationDto clientRegistrationDto) {
        return clientRepository.insertClient(clientRegistrationDto);
    }

    public boolean deleteById(Long id) {
        return clientRepository.deleteById(id);
    }

    public Map<Long, Client> getAllClientsNoLimit() {
        List<Client> allClientsNoLimit = clientRepository.getAllClientsNoLimit();
        return allClientsNoLimit.stream().collect(Collectors.toMap(User::getId, Function.identity()));
    }

    public List<Client> getAll(int page) {
        int index = getIndex(page);
        return clientRepository.getAll(index);
    }

    public double getCountOfPagesForAll() {
        double countOfFields = clientRepository.getCountOfFieldsForAll();
        return getCountOfPages(countOfFields);
    }

    public List<Client> getClientsBySearchParameters(ClientSearchDto clientSearchDto) {
        int index = getIndex(clientSearchDto.getPage());
        return clientRepository.getClientsBySearchParameters(clientSearchDto, index);
    }

    public double getCountOfPagesForSearchParameters(ClientSearchDto clientSearchDto) {
        double countOfFields = clientRepository.getCountOfFieldsForSearchParameters(clientSearchDto);
        return getCountOfPages(countOfFields);
    }

    private double getCountOfPages(double countOfField) {
        return Math.ceil(countOfField / 5);
    }

    private int getIndex(int page) {
        return (page - 1) * 5;
    }
}
