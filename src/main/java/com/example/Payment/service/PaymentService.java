package com.example.Payment.service;

import com.example.Payment.model.Client;
import com.example.Payment.model.Log;
import com.example.Payment.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {
    private final CheckAccountRepository checkAccountRepository;
    private final LoggedUserManagementService loggedUserManagementService;
    private final ChangeAccountRepository changeAccountRepository;
    private final ChangeRefundRepository changeRefundRepository;
    private final CheckQuantityRefundRepository checkQuantityRefundRepository;
    private final CreateLogRepository createLogRepository;

    public PaymentService(CheckAccountRepository checkAccountRepository, LoggedUserManagementService loggedUserManagementService,
                          ChangeAccountRepository changeAccountRepository,  ChangeRefundRepository changeRefundRepository,
                          CheckQuantityRefundRepository checkQuantityRefundRepository, CreateLogRepository createLogRepository) {
        this.checkAccountRepository = checkAccountRepository;
        this.loggedUserManagementService = loggedUserManagementService;
        this.changeAccountRepository = changeAccountRepository;
        this.changeRefundRepository = changeRefundRepository;
        this.checkQuantityRefundRepository = checkQuantityRefundRepository;
        this.createLogRepository = createLogRepository;
    }

    public Client getClientByUsername() {
        List<Client> clients = checkAccountRepository.findByUsername(loggedUserManagementService.getUsername());
        if (clients.isEmpty()) {
            return null;
        }
        return clients.get(0);
    }

    @Transactional
    public String paymentClient(Client client, List<String> items) {
        BigDecimal amount = client.getAmount();
        int value1 = 0;
        int value2 = 0;
        int value3 = 0;

        for (String item : items) {
            if (item.equals("1000")) {
                value1 += 1;
            }
            else if (item.equals("2000")) {
                value2 += 1;
            }
            else {
                value3 += 1;
            }
            BigDecimal itemPrice = new BigDecimal(item);
            if (amount.subtract(itemPrice).compareTo(BigDecimal.ZERO) < 0) {
                return "잔액이 부족합니다.";
            }
            else {
                amount = amount.subtract(itemPrice);
            }
        }
        if (value1 != 0) {
            int quantity1 = checkQuantityRefundRepository.getItemQuantity(client.getId(), "1000");
            changeRefundRepository.updateRefundAccount(client.getId(),value1+quantity1,"1000");
            Log log1 = new Log();
            log1.setId(client.getId());
            log1.setItem("1000");
            log1.setStatus("결제");
            createLogRepository.createLog(log1);
        }
        if (value2 != 0) {
            int quantity2 = checkQuantityRefundRepository.getItemQuantity(client.getId(), "2000");
            changeRefundRepository.updateRefundAccount(client.getId(),value2+quantity2,"2000");
            Log log2 = new Log();
            log2.setId(client.getId());
            log2.setItem("2000");
            log2.setStatus("결제");
            createLogRepository.createLog(log2);
        }
        if (value3 != 0) {
            int quantity3 = checkQuantityRefundRepository.getItemQuantity(client.getId(), "3000");
            changeRefundRepository.updateRefundAccount(client.getId(),value3+quantity3,"3000");
            Log log3 = new Log();
            log3.setId(client.getId());
            log3.setItem("3000");
            log3.setStatus("결제");
            createLogRepository.createLog(log3);
        }
        changeAccountRepository.updateAccount(client.getUsername(), amount);
        return "남은 잔액 " + amount.toString();
    }

    public String addAmount(Client client,BigDecimal money) {
        BigDecimal amount = client.getAmount().add(money);
        changeAccountRepository.updateAccount(client.getUsername(), amount);
        return "입금 완료 " + amount.toString();
    }

    public String checkAmount(Client client) {
        BigDecimal amount = client.getAmount();
        return "잔액 " + amount.toString();
    }
}
