package com.ProjetoDSbancario.Projeto_DS.dtos;

import java.time.format.DateTimeFormatter;

import com.ProjetoDSbancario.Projeto_DS.models.Lancamento;
import com.ProjetoDSbancario.Projeto_DS.models.enums.TipoLancamento;
import com.ProjetoDSbancario.Projeto_DS.models.enums.TipoOperacao;


public class BankStatementDTO {
    private String Data;
    private String valor;
    private TipoLancamento tipo;
    private TipoOperacao tipoOperacao;

    public BankStatementDTO() {}

    public BankStatementDTO(Lancamento lancamento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.Data = lancamento.getData().format(formatter);
        this.valor = lancamento.getValor().toString();
        this.tipo = lancamento.getTipo();
        this.tipoOperacao = lancamento.getTipoOperacao();
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
    

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

}
