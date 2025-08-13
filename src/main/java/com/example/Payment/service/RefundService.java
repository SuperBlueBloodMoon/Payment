package com.example.Payment.service;

import com.example.Payment.model.Client;
import com.example.Payment.model.ItemCount;
import com.example.Payment.model.Log;
import com.example.Payment.model.Purchased;
import com.example.Payment.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RefundService {
    private final CheckRefundRepository checkRefundRepository;
    private final CreateRefundRepository createRefundRepository;
    private final ChangeRefundRepository changeRefundRepository;
    private final CheckQuantityRefundRepository checkQuantityRefundRepository;
    private final ChangeAccountRepository changeAccountRepository;
    private final LoginRepository loginRepository;
    private final CreateLogRepository createLogRepository;

    public RefundService(CheckRefundRepository checkRefundRepository, CreateRefundRepository createRefundRepository,
                         ChangeRefundRepository changeRefundRepository, CheckQuantityRefundRepository checkQuantityRefundRepository,
                         ChangeAccountRepository changeAccountRepository, LoginRepository loginRepository, CreateLogRepository createLogRepository) {
        this.checkRefundRepository = checkRefundRepository;
        this.createRefundRepository = createRefundRepository;
        this.changeRefundRepository = changeRefundRepository;
        this.checkQuantityRefundRepository = checkQuantityRefundRepository;
        this.changeAccountRepository = changeAccountRepository;
        this.loginRepository = loginRepository;
        this.createLogRepository = createLogRepository;
    }

    public List<ItemCount> getItemCount(String id) {
        return checkRefundRepository.getItemCount(id);
    }

    public void createPurchasedRefund(String id){
        int value = 1000;
        for (int i = 1; i <= 3; i++) {
            Purchased purchased = new Purchased();
            purchased.setId(id);
            purchased.setItem(String.valueOf(i*value));
            purchased.setQuantity(0);
            purchased.setPurchased_id(0);
            createRefundRepository.createRefund(purchased);
        }
    }

    public String purchasedRefund(String id, String item, int quantity){
        int value = checkQuantityRefundRepository.getItemQuantity(id, item);
        if (value - quantity < 0){
            return "환불 불가";
        }
        for (int i = 0; i < quantity; i++) {
            Log log = new Log();
            log.setId(id);
            log.setItem(item);
            log.setStatus("환불");
            createLogRepository.createLog(log);
        }
        changeRefundRepository.updateRefundAccount(id,value-quantity,item);
        BigDecimal mulValue = new BigDecimal(quantity);
        BigDecimal refundAmount = new BigDecimal(item).multiply(mulValue);
        List<Client> Clients = loginRepository.findByClientId(id);
        Client client = Clients.get(0);
        BigDecimal amount = client.getAmount();
        changeAccountRepository.updateAccount(id, refundAmount.add(amount));
        return "환불 완료" + refundAmount;
    }
}
