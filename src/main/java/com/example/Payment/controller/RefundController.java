package com.example.Payment.controller;

import com.example.Payment.model.Client;
import com.example.Payment.model.ItemCount;
import com.example.Payment.service.PaymentService;
import com.example.Payment.service.RefundService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class RefundController {
    private final RefundService refundService;
    private final PaymentService paymentService;
    public RefundController(RefundService refundService,
                            PaymentService paymentService) {
        this.refundService = refundService;
        this.paymentService = paymentService;
    }

    @PostMapping("/refund")
    public String refundAccount(
            @RequestParam Map<String, String> selectedValue,
            RedirectAttributes redirectAttributes){
        Client client = paymentService.getClientByUsername();
        String value1 = selectedValue.get("삼성 라이온즈(1000)");
        int value1000 =  Integer.parseInt(value1);
        if (value1000 > 0){
            refundService.purchasedRefund(client.getId(),"1000",value1000);
        }
        String value2 = selectedValue.get("기아 타이거즈(2000)");
        int value2000 =  Integer.parseInt(value2);
        if (value2000 > 0){
            refundService.purchasedRefund(client.getId(),"2000",value2000);
        }
        String value3 = selectedValue.get("한화 이글스(3000)");
        int value3000 =  Integer.parseInt(value3);
        if (value3000 > 0){
            refundService.purchasedRefund(client.getId(),"3000",value3000);
        }

        List<ItemCount> item0 = refundService.getItemCount(client.getId());
        redirectAttributes.addFlashAttribute("item1", "삼성 라이온즈 : ");
        redirectAttributes.addFlashAttribute("item2", "기아 타이거즈 : ");
        redirectAttributes.addFlashAttribute("item3", "한화 이글스 : ");
        redirectAttributes.addFlashAttribute("value1", item0.get(0).quantity());
        redirectAttributes.addFlashAttribute("value2", item0.get(1).quantity());
        redirectAttributes.addFlashAttribute("value3", item0.get(2).quantity());
        return "redirect:/main";
    }

}
