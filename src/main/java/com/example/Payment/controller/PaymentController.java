package com.example.Payment.controller;

import com.example.Payment.model.Client;
import com.example.Payment.model.ItemCount;
import com.example.Payment.service.PaymentService;
import com.example.Payment.service.RefundService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;


@Controller
public class PaymentController {
    private final PaymentService paymentService;
    private final RefundService refundService;

    public PaymentController(PaymentService paymentService, RefundService refundService) {
        this.paymentService = paymentService;
        this.refundService = refundService;
    }

    @GetMapping("/payment")
    public String payment(@RequestParam("items") List<String> items,
            RedirectAttributes redirectAttributes) {
        Client client = paymentService.getClientByUsername();
        String result = paymentService.paymentClient(client, items);
        redirectAttributes.addFlashAttribute("result", result);

        List<ItemCount> item0 = refundService.getItemCount(client.getId());
        redirectAttributes.addFlashAttribute("item1", "삼성 라이온즈 : ");
        redirectAttributes.addFlashAttribute("item2", "기아 타이거즈 : ");
        redirectAttributes.addFlashAttribute("item3", "한화 이글스 : ");
        redirectAttributes.addFlashAttribute("value1", item0.get(0).quantity());
        redirectAttributes.addFlashAttribute("value2", item0.get(1).quantity());
        redirectAttributes.addFlashAttribute("value3", item0.get(2).quantity());
        return "redirect:/main";
    }

    @PostMapping("/addMoney")
    public String addMoney(
            @RequestParam("money") BigDecimal money,
            RedirectAttributes redirectAttributes) {
        Client client = paymentService.getClientByUsername();
        String result = paymentService.addAmount(client,money);
        redirectAttributes.addFlashAttribute("result", result);

        return "redirect:/main";
    }

    @GetMapping("checkAmount")
    public String checkAmount(
            RedirectAttributes redirectAttributes) {
        Client client = paymentService.getClientByUsername();
        String result = paymentService.checkAmount(client);
        redirectAttributes.addFlashAttribute("result", result);

        return "redirect:/main";
    }
}
