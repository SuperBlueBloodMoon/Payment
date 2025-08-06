package com.example.Payment.controller;

import com.example.Payment.model.Client;
import com.example.Payment.model.ItemCount;
import com.example.Payment.repository.CheckAccountRepository;
import com.example.Payment.repository.CreateAccountRepository;
import com.example.Payment.service.LoggedUserManagementService;
import com.example.Payment.service.LoginService;
import com.example.Payment.service.RefundService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class LoginController {
    private final LoginService loginService;
    private final CreateAccountRepository createAccountRepository;
    private final LoggedUserManagementService loggedUserManagementService;
    private final RefundService refundService;
    private final CheckAccountRepository checkAccountRepository;

    public LoginController(LoginService loginService, CreateAccountRepository createAccountRepository,
                           LoggedUserManagementService loggedUserManagementService, RefundService refundService,
                           CheckAccountRepository checkAccountRepository) {
        this.loginService = loginService;
        this.createAccountRepository = createAccountRepository;
        this.loggedUserManagementService = loggedUserManagementService;
        this.refundService = refundService;
        this.checkAccountRepository = checkAccountRepository;
    }
    @GetMapping("/")
    public String loginGet() {
        return "login";
    }


    @PostMapping("/")
    public String loginPost(
            @ModelAttribute Client client) {
        boolean result = false;
        result = loginService.login(client.getPassword(),client.getId());
        if (result) {
            return "redirect:/main";
        }
        else {
            return "login";
        }
    }

    @GetMapping("/create")
    public String createGet() {
        return "create";
    }

    @PostMapping("/create")
    public String createPost(
            @ModelAttribute Client client) {
        createAccountRepository.createAccount(client);
        refundService.createPurchasedRefund(client.getId());
        return "login";
    }

    @GetMapping("/main")
    public String mainGet(
            @RequestParam(required = false) String logout,
            Model model) {
        if (logout != null) {
            loggedUserManagementService.setUsername(null);
        }
        String username = loggedUserManagementService.getUsername();
        if (username == null) {
            return "redirect:/";
        }
        List<Client> clients = checkAccountRepository.findByUsername(username);
        Client client = clients.get(0);
        List<ItemCount> items = refundService.getItemCount(client.getId());
        model.addAttribute("item1", "삼성 라이온즈 : ");
        model.addAttribute("item2", "기아 타이거즈 : ");
        model.addAttribute("item3", "한화 이글스 : ");
        model.addAttribute("value1", items.get(0).quantity());
        model.addAttribute("value2", items.get(1).quantity());
        model.addAttribute("value3", items.get(2).quantity());

        Map<String, List<String>> selectOptions = new LinkedHashMap<>();
        int value2 = 0;
        for (int j = 0; j < 3; j++) {
            List<String> value1 = new ArrayList<>();
            for (int i = 0; i <= items.get(j).quantity(); i++) {
                value1.add(Integer.toString(i));
            }
            value2 = 1000 + (1000 * j);
            String value3 = "";
            if (value2 == 1000) {
                value3 = "삼성 라이온즈(1000)";
            }
            else if (value2 == 2000) {
                value3 = "기아 타이거즈(2000)";
            }
            else if (value2 == 3000) {
                value3 = "한화 이글스(3000)";
            }
            selectOptions.put(value3, value1);
        }
        model.addAttribute("values", selectOptions);
        return "main";
    }
}
