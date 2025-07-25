package com.ProjetoDSbancario.Projeto_DS.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProjetoDSbancario.Projeto_DS.models.authentication.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByCpf(String cpf);
    
    boolean existsByCpf(String cpf);
}
