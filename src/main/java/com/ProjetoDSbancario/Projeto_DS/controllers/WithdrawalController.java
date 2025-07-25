package com.ProjetoDSbancario.Projeto_DS.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProjetoDSbancario.Projeto_DS.dtos.LancamentoDTO;
import com.ProjetoDSbancario.Projeto_DS.services.WithdrawalService;


@RestController
@RequestMapping("/withdrawals")
public class WithdrawalController {

    private WithdrawalService withdrawalService;

    WithdrawalController(WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @PostMapping("")
    public ResponseEntity<Void> withdraw(@RequestBody LancamentoDTO withdrawDTO) {

        this.withdrawalService.withdraw(withdrawDTO);

        return ResponseEntity.ok().build();
    }

}
