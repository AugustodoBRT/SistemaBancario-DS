package com.ProjetoDSbancario.Projeto_DS.models;

import java.util.HashSet;
import java.util.Set;

import com.ProjetoDSbancario.Projeto_DS.dtos.UserRequestDTO;
import com.ProjetoDSbancario.Projeto_DS.models.authentication.Role;
import com.ProjetoDSbancario.Projeto_DS.models.authentication.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tbCliente")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String nome;

    @Column(length = 200)
    private String email;

    @Column(length = 20)
    private String telefone;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Conta> conta = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL) 
    @JoinColumn(name = "idUser")
    private User user;

    Client() {}

    public Client(UserRequestDTO userDTO, String senha, Set<Role> roles){
        this.nome = userDTO.getNome();
        this.telefone = userDTO.getTelefone();
        this.email = userDTO.getEmail();

        User user = new User(userDTO,senha,roles);

        this.user = user;
    }

    public String getNome() {   
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Conta> getConta() {
        return conta;
    }

    public void setConta(Set<Conta> conta) {
        this.conta = conta;
    }
}
