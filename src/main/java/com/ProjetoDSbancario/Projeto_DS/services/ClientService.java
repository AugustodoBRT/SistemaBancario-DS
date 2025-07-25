package com.ProjetoDSbancario.Projeto_DS.services;

import org.springframework.stereotype.Service;

import com.ProjetoDSbancario.Projeto_DS.models.Client;
import com.ProjetoDSbancario.Projeto_DS.models.authentication.User;
import com.ProjetoDSbancario.Projeto_DS.repositories.ClientRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;

    }

    public Client findByUser(User user) {
        return this.clientRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }
}
