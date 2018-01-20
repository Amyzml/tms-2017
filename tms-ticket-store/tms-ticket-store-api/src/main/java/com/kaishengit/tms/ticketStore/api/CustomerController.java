package com.kaishengit.tms.ticketStore.api;

import com.kaishengit.tms.entity.Customer;
import com.kaishengit.tms.entity.Ticket;
import com.kaishengit.tms.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author NativeBoy
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private TicketService ticketService;

    /**
     * 根据年票
     * @param id 年票id
     * @param model
     * @return
     */
    @GetMapping("/detail/{id:\\d+}")
    public String detail(@PathVariable Integer id, Model model){
        Customer customer = ticketService.findCustomerByTicketId(id);
        Ticket ticket = ticketService.findTicketByCustomerId(customer.getId());
        model.addAttribute("ticket",ticket);
        model.addAttribute("customer",customer);
        return "store/detail";
    }
}
