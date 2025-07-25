package com.ProjetoDSbancario.Projeto_DS.dtos;

import java.math.BigDecimal;

import com.ProjetoDSbancario.Projeto_DS.models.Conta;

public class ContaDTO {
    
    private Long id;
    private String numero;
    private BigDecimal saldo;
    private Double limiteCredito;


    public ContaDTO() {}

    public ContaDTO(Conta conta){
        this.id = conta.getId();
        this.numero = conta.getNumero();
        
        // soma o saldo de todos os lancamentos pra ver o estado atual da conta
        this.saldo = conta.getLancamentos().stream()
        .map(lancamento -> {
            return switch (lancamento.getTipo()) {
                case CREDITO -> lancamento.getValor();
                case DEBITO -> lancamento.getValor().negate(); // subtrai caso encontre uma operacao de saque
                default -> BigDecimal.ZERO;
            }; 
        })
        .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.limiteCredito = conta.getLimiteCredito();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(Double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

}
