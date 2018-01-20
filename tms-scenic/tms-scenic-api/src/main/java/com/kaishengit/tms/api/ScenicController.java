package com.kaishengit.tms.api;

import com.kaishengit.tms.entity.Customer;
import com.kaishengit.tms.entity.ScenicAccount;

import com.kaishengit.tms.service.CheckTicketService;
import com.kaishengit.tms.service.TicketConsumerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 景区验票
 * @author NativeBoy
 */
@Controller
@RequestMapping("/checkTicket")
public class ScenicController {


    @Autowired
    private CheckTicketService checkTicketService;
    @Autowired
    private TicketConsumerService ticketConsumerService;

    /**
     * 获取当前登录对象
     * @return
     */
    private ScenicAccount getCurrAccount(){
        Subject subject = SecurityUtils.getSubject();
        return (ScenicAccount) subject.getPrincipal();
    }

    /**
     * 进入验票界面
     * @return
     */
    @GetMapping("/check")
    public String checkout(){
        return "/check/slotCard";
    }

    /**
     * 验票: 根据刷卡信息验票
     * @param ticketNum 年票卡号
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/check")
    public String checkTicket(String ticketNum, RedirectAttributes redirectAttributes){
        Customer customer = null;
        try {
            checkTicketService.checkTicketByTicketNum(ticketNum);
            customer = checkTicketService.findCustomerByTicketNum(ticketNum);

            ticketConsumerService.saveNewConsumerOrder(customer.getTicketId(),getCurrAccount().getId());

            redirectAttributes.addFlashAttribute("message","验票通过");
            return "redirect:/checkTicket/cusDetail/" + customer.getId();
        }catch (RuntimeException ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/checkTicket/check";
        }
    }

    /**
     * 出错的界面
     * @return
     */
    @GetMapping("/error")
    public String errorMessage(@RequestParam String message, Model model){
        model.addAttribute("message",message);
        return "check/error";
    }

    /**
     * 验票成功,展示客户头像,名字,年龄
     * @param id 顾客id
     * @param model
     * @return
     */
    @GetMapping("/cusDetail/{id:\\d+}")
    public String customerDetail(@PathVariable Integer id, Model model){
        Customer customer = checkTicketService.findCustomerByCustomerId(id);
        model.addAttribute("customer",customer);
        return "check/cusDetail";
    }

    /**
     * 进入参数配置界面
     * @return
     */
    @GetMapping("/config")
    public String config(){
        return "/check/config";
    }
}
