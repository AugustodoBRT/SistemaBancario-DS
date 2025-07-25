package com.ProjetoDSbancario.Projeto_DS.controllers;

import java.security.SecureRandom;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProjetoDSbancario.Projeto_DS.models.Client;
import com.ProjetoDSbancario.Projeto_DS.models.authentication.User;
import com.ProjetoDSbancario.Projeto_DS.services.ClientService;
import com.ProjetoDSbancario.Projeto_DS.services.UserService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/ping")
    public ResponseEntity<Void> ping() {
        System.out.println("API online");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/update-password")
    public ResponseEntity<String> redefinirSenha(@RequestBody Map<String, String> request) {
        String cpf = request.get("cpf");
        if (cpf == null || cpf.isEmpty()) {
            return ResponseEntity.badRequest().body("CPF é obrigatório.");
        }

        User user = this.userService.findByCpf(cpf);

        Client client = this.clientService.findByUser(user);

        String novaSenha = gerarSenhaAleatoria(10);

        enviarNovaSenha(client.getEmail(), novaSenha);

        this.userService.updatePassword(user,novaSenha);

        return ResponseEntity.ok("");
    }

    public void enviarNovaSenha(String email, String novaSenha) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(email);
        mensagem.setSubject("Nova senha gerada");
        mensagem.setText("Sua nova senha é: " + novaSenha);
        mailSender.send(mensagem);
    }

    public String gerarSenhaAleatoria(int tamanho) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder senha = new StringBuilder();

        for (int i = 0; i < tamanho; i++) {
            senha.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }

        return senha.toString();
    }

}
