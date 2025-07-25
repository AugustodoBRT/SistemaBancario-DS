package com.ProjetoDSbancario.Projeto_DS.dtos;

public class JwtTokenDTO {
    private String token;

    JwtTokenDTO() {}

    public JwtTokenDTO(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
