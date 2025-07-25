package com.ProjetoDSbancario.Projeto_DS.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ProjetoDSbancario.Projeto_DS.dtos.BankStatementDTO;
import com.ProjetoDSbancario.Projeto_DS.models.Conta;
import com.ProjetoDSbancario.Projeto_DS.models.Lancamento;
import com.ProjetoDSbancario.Projeto_DS.models.enums.TipoLancamento;
import com.ProjetoDSbancario.Projeto_DS.repositories.AccountsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BankStatementsService {

    private AccountsRepository accountsRepository;

    BankStatementsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }
    
    public List<BankStatementDTO> getAccountBankStatements(String numeroConta) {
        
        Conta conta = this.accountsRepository.findByNumero(numeroConta).orElseThrow(() -> new EntityNotFoundException());

        List<BankStatementDTO> bankStatements = new ArrayList<>(conta.getLancamentos().size());

        conta.getLancamentos().forEach(lancamento -> {
            BankStatementDTO bankStatementDTO = new BankStatementDTO(lancamento);
            bankStatements.add(bankStatementDTO);
        });

        return bankStatements;
    }


    public BigDecimal getSaldoAllClientAccounts(Long clienteId) {
        
        List<Conta> accounts = this.accountsRepository.findByClienteId(clienteId);

        BigDecimal sum = new BigDecimal(0);

        for (Conta conta : accounts) {
            for (Lancamento lancamento : conta.getLancamentos()) {
                if (lancamento.getTipo() == TipoLancamento.CREDITO) {
                    sum = sum.add(lancamento.getValor());
                } else {
                    sum = sum.add((lancamento.getValor().multiply(new BigDecimal(-1))));
                }
            }
        }

        return sum;
    }
}
