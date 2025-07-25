package com.ProjetoDSbancario.Projeto_DS.dtos;

import java.time.LocalDateTime;

public class HistoryDTO {

    private LocalDateTime data;
    private String ip;

    public HistoryDTO() {}

    public HistoryDTO(LocalDateTime data, String ip) {
        this.data = data;
        this.ip = ip;
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
}