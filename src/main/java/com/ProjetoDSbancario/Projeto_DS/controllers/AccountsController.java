package com.ProjetoDSbancario.Projeto_DS.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ProjetoDSbancario.Projeto_DS.dtos.ChavePixDTO;
import com.ProjetoDSbancario.Projeto_DS.dtos.ContaDTO;
import com.ProjetoDSbancario.Projeto_DS.dtos.LimitDTO;
import com.ProjetoDSbancario.Projeto_DS.dtos.TransferenceDTO;
import com.ProjetoDSbancario.Projeto_DS.services.AccountsService;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private AccountsService accountsService;

    AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping("")
    public ResponseEntity<List<ContaDTO>> getUserAccounts() {
        return ResponseEntity.ok(this.accountsService.getUserAccounts());
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContaDTO>> getAllAccounts() {
        return ResponseEntity.ok(this.accountsService.getAllAccounts());
    }

    @PostMapping("")
    public ResponseEntity<ContaDTO> createUserAccount() throws URISyntaxException {

        ContaDTO createdAccountDTO = this.accountsService.createUserAccount();

        URI location = URI.create("/accounts/" + createdAccountDTO.getId());

        return ResponseEntity.created(location).body(createdAccountDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> getUserAccountById(@PathVariable Long id) {

        return ResponseEntity.ok(this.accountsService.getUserAccountById(id));
    }

    @PutMapping("/limit")
    public ResponseEntity<Void> updateLimit(@RequestBody LimitDTO limitDTO) {

        this.accountsService.updateLimit(limitDTO);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/pix")
    public ResponseEntity<Void> updatePix(@RequestBody ChavePixDTO chavePixDTO) {

        this.accountsService.updatePix(chavePixDTO);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/transference")
    public ResponseEntity<Void> transference(@RequestBody TransferenceDTO transferenceDTO) {

        this.accountsService.transference(transferenceDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/pix")
    public ResponseEntity<Void> pix(@RequestBody TransferenceDTO transferenceDTO) {

        this.accountsService.pix(transferenceDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/pix")
    public ResponseEntity<ContaDTO> getAccountByPixKey(@RequestParam String chavePix) {
        ContaDTO contaDTO = accountsService.getAccountByPixKey(chavePix);
        return ResponseEntity.ok(contaDTO);
    }
}
