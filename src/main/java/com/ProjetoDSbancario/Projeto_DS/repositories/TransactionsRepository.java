package com.ProjetoDSbancario.Projeto_DS.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProjetoDSbancario.Projeto_DS.models.Lancamento;


@Repository
public interface TransactionsRepository extends JpaRepository<Lancamento,Long>{
    
}
