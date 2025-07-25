package com.ProjetoDSbancario.Projeto_DS.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProjetoDSbancario.Projeto_DS.dtos.BankStatementDTO;
import com.ProjetoDSbancario.Projeto_DS.services.BankStatementsService;


@RestController
@RequestMapping("/bankstatements")
public class BankStatementsController {

    private BankStatementsService bankStatementsService;

    BankStatementsController(BankStatementsService bankStatementsService) {
        this.bankStatementsService = bankStatementsService;
    }

    @GetMapping("/{numeroConta}")
    public ResponseEntity<List<BankStatementDTO>> getAccountBankStatements(@PathVariable String numeroConta) {
        return ResponseEntity.ok(this.bankStatementsService.getAccountBankStatements(numeroConta));
    }
}
