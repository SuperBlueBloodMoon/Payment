package com.example.Payment.service;

import com.example.Payment.model.Client;
import com.example.Payment.repository.LoginRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Service
@RequestScope

public class LoginService {
    private final LoginRepository loginRepository;
    private final LoggedUserManagementService loggedUserManagementService;

    public LoginService(LoginRepository loginRepository, LoggedUserManagementService loggedUserManagementService) {
        this.loginRepository = loginRepository;
        this.loggedUserManagementService = loggedUserManagementService;
    }

    public boolean login(String password, String id) {
        List<Client> loginRequest = loginRepository.findByClientId(id);
        if (loginRequest.isEmpty()) {
            return false;
        }
        Client client = loginRequest.get(0);
        if (client.getPassword().equals(password)) {
            loggedUserManagementService.setUsername(client.getUsername());
            return true;
        }
        return false;
    }
}
