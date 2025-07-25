package com.ProjetoDSbancario.Projeto_DS.models;

import java.time.LocalDateTime;

import com.ProjetoDSbancario.Projeto_DS.models.authentication.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbHistory")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;

    private String ip;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public History() {
    }

    public History(LocalDateTime data, String ip, User user) {
        this.data = data;
        this.ip = ip;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}