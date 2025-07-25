package com.ProjetoDSbancario.Projeto_DS.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProjetoDSbancario.Projeto_DS.models.History;
import com.ProjetoDSbancario.Projeto_DS.models.authentication.User;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUser(User user);
}
