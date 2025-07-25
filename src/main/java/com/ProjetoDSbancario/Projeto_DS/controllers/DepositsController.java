package com.ProjetoDSbancario.Projeto_DS.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProjetoDSbancario.Projeto_DS.dtos.LancamentoDTO;
import com.ProjetoDSbancario.Projeto_DS.services.DepositsService;


@RestController
@RequestMapping("/deposits")
public class DepositsController {

    private DepositsService depositsService;

    DepositsController(DepositsService depositsService) {
        this.depositsService = depositsService;
    }

    @PostMapping("")
    public ResponseEntity<Void> deposit(@RequestBody LancamentoDTO depositDTO) {

        this.depositsService.deposit(depositDTO);

        return ResponseEntity.ok().build();
    }

}
