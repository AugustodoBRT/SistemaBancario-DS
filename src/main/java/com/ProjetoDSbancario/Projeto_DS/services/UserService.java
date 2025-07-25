package com.ProjetoDSbancario.Projeto_DS.services;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ProjetoDSbancario.Projeto_DS.configurations.SecurityConfiguration;
import com.ProjetoDSbancario.Projeto_DS.dtos.HistoryDTO;
import com.ProjetoDSbancario.Projeto_DS.dtos.JwtTokenDTO;
import com.ProjetoDSbancario.Projeto_DS.dtos.LoginUserDTO;
import com.ProjetoDSbancario.Projeto_DS.dtos.UserRequestDTO;
import com.ProjetoDSbancario.Projeto_DS.dtos.UserResponseDTO;
import com.ProjetoDSbancario.Projeto_DS.exceptions.InvalidCpfException;
import com.ProjetoDSbancario.Projeto_DS.models.Client;
import com.ProjetoDSbancario.Projeto_DS.models.History;
import com.ProjetoDSbancario.Projeto_DS.models.authentication.Role;
import com.ProjetoDSbancario.Projeto_DS.models.authentication.User;
import com.ProjetoDSbancario.Projeto_DS.models.authentication.UserDetailsImpl;
import com.ProjetoDSbancario.Projeto_DS.models.enums.authentication.RoleName;
import com.ProjetoDSbancario.Projeto_DS.repositories.ClientRepository;
import com.ProjetoDSbancario.Projeto_DS.repositories.HistoryRepository;
import com.ProjetoDSbancario.Projeto_DS.repositories.UserRepository;
import com.ProjetoDSbancario.Projeto_DS.services.authentication.JwtTokenService;
import com.ProjetoDSbancario.Projeto_DS.services.authentication.UserDetailsServiceImpl;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    private ClientRepository clientRepository;
    private UserRepository userRepository;
    private SecurityConfiguration securityConfiguration;
    private AuthenticationManager authenticationManager;
    private JwtTokenService jwtTokenService;
    private UserDetailsServiceImpl userDetailsService;
    private HistoryRepository historyRepository;

    UserService(ClientRepository clientRepository, SecurityConfiguration securityConfiguration,
            AuthenticationManager authenticationManager,
            JwtTokenService jwtTokenService, UserDetailsServiceImpl userDetailsService, UserRepository userRepository,
            HistoryRepository historyRepository) {
        this.clientRepository = clientRepository;
        this.securityConfiguration = securityConfiguration;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    public UserResponseDTO getUserInfo() {
        User user = this.userDetailsService.getLoggedUser();

        Client client = this.clientRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        UserResponseDTO userResponseDTO = new UserResponseDTO(client.getNome());

        return userResponseDTO;
    }

    public List<HistoryDTO> getUserHistory() {
        User user = this.userDetailsService.getLoggedUser();

        List<History> historyList = this.historyRepository.findByUser(user);

        List<HistoryDTO> historyResponseDTO = new LinkedList<>();

        for (History history : historyList) {
            HistoryDTO dto = new HistoryDTO(history.getData(), history.getIp());
            historyResponseDTO.add(dto);
        }

        return historyResponseDTO;
    }

    public JwtTokenDTO authenticateUser(LoginUserDTO loginUserDTO, String ip) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginUserDTO.getCpf(), loginUserDTO.getSenha());

        // lanca BadCredentialsException em caso de falha
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        History log = new History(
                LocalDateTime.now(),
                ip,
                userDetails.getUser());

        historyRepository.save(log);

        return new JwtTokenDTO(jwtTokenService.generateToken(userDetails));
    }

    public void registerUser(UserRequestDTO registerUserDTO) {
        // verificacao de CPF
        boolean cpfAlreadyExists = this.userRepository.existsByCpf(registerUserDTO.getCpf());

        if (cpfAlreadyExists) {
            throw new InvalidCpfException("CPF já registrado");
        }

        Role userRole = new Role();
        userRole.setName(RoleName.ROLE_USER);

        Client newClient = new Client(registerUserDTO,
                securityConfiguration.passwordEncoder().encode(registerUserDTO.getSenha()),
                Set.of(userRole));

        this.clientRepository.save(newClient);
    }

    public void updatePassword(User user, String newPassword) {

        String cripPassword =  securityConfiguration.passwordEncoder().encode(newPassword);

        user.setSenha(cripPassword);
        
        this.userRepository.save(user);
    }

    public User findByCpf(String cpf){
        User user = this.userRepository.findByCpf(cpf).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return user;
    }
}
