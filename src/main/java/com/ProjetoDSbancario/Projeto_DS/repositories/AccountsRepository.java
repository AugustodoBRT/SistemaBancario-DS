package com.ProjetoDSbancario.Projeto_DS.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProjetoDSbancario.Projeto_DS.models.Conta;

@Repository
public interface AccountsRepository extends JpaRepository<Conta,Long>{
    List<Conta> findByClienteUserId(Long userId);

    List<Conta> findByClienteId(Long clienteId);

    boolean existsByNumero(String numero);

    Optional<Conta> findByNumero(String numero);

    Optional<Conta> findByChavePix(String chavePix);
}
