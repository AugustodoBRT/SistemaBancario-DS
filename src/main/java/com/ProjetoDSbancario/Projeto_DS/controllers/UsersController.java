package com.ProjetoDSbancario.Projeto_DS.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProjetoDSbancario.Projeto_DS.dtos.HistoryDTO;
import com.ProjetoDSbancario.Projeto_DS.dtos.JwtTokenDTO;
import com.ProjetoDSbancario.Projeto_DS.dtos.LoginUserDTO;
import com.ProjetoDSbancario.Projeto_DS.dtos.UserRequestDTO;
import com.ProjetoDSbancario.Projeto_DS.dtos.UserResponseDTO;
import com.ProjetoDSbancario.Projeto_DS.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UsersController {
    
    private UserService userService;

    UsersController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/me")   
    public ResponseEntity<UserResponseDTO> getUserInfo() {

        UserResponseDTO userResponseDTO = this.userService.getUserInfo();

        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/history")   
    public ResponseEntity<List<HistoryDTO>> getUserHistory() {

        List<HistoryDTO> historyResponseDTO = this.userService.getUserHistory();

        return ResponseEntity.ok(historyResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> authenticateUser(@RequestBody LoginUserDTO loginUserDTO, HttpServletRequest request) {

        String userIp = request.getRemoteAddr();

        JwtTokenDTO token = userService.authenticateUser(loginUserDTO,userIp);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserRequestDTO registerUserDTO) {

        this.userService.registerUser(registerUserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



}
