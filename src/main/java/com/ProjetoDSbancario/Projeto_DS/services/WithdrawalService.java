package com.ProjetoDSbancario.Projeto_DS.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.ProjetoDSbancario.Projeto_DS.dtos.LancamentoDTO;
import com.ProjetoDSbancario.Projeto_DS.exceptions.InvalidTransactionException;
import com.ProjetoDSbancario.Projeto_DS.models.Conta;
import com.ProjetoDSbancario.Projeto_DS.models.Lancamento;
import com.ProjetoDSbancario.Projeto_DS.models.enums.TipoLancamento;
import com.ProjetoDSbancario.Projeto_DS.models.enums.TipoOperacao;
import com.ProjetoDSbancario.Projeto_DS.repositories.AccountsRepository;
import com.ProjetoDSbancario.Projeto_DS.repositories.TransactionsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class WithdrawalService {

    private TransactionsRepository transactionsRepository;
    private AccountsRepository accountsRepository;

    WithdrawalService (AccountsRepository accountsRepository, TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
        this.accountsRepository = accountsRepository;
    }
    
    public void withdraw(LancamentoDTO withdrawalDTO) {

        Conta conta = this.accountsRepository.findByNumero(withdrawalDTO.getNumeroConta()).orElseThrow(() -> new EntityNotFoundException());

        BigDecimal saldo = conta.getLancamentos().stream()
        .map(lancamento -> {
            return switch (lancamento.getTipo()) {
                case CREDITO -> lancamento.getValor();
                case DEBITO -> lancamento.getValor().negate(); // subtrai caso encontre uma operacao de saque
                default -> BigDecimal.ZERO;
            }; 
        })
        .reduce(BigDecimal.ZERO, BigDecimal::add);

        // soma o saldo com o limite de credito da conta
        saldo = saldo.add(new BigDecimal(conta.getLimiteCredito()));

        BigDecimal valor = new BigDecimal(withdrawalDTO.getValor());

        // soma o valor com o limite de credito da conta

        if (valor.compareTo(saldo) > 0){
            throw new InvalidTransactionException("O saldo atual somado com o limite de crédito é menor do que a tentativa de saque");
        }

        withdrawalDTO.setTipoLancamento(TipoLancamento.DEBITO);
        withdrawalDTO.setTipoOperacao(TipoOperacao.SAQUE);

        Lancamento withDrawal = new Lancamento(withdrawalDTO,conta);

        this.transactionsRepository.save(withDrawal);
    }
}
