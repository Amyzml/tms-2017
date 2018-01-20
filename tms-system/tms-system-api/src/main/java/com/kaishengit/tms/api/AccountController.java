package com.kaishengit.tms.api;

import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.entity.Role;
import com.kaishengit.tms.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.inject.Inject;

import java.util.List;

/**
 * @author NativeBoy
 */
@Controller
@RequestMapping("/account/")
public class AccountController {

    @Inject
    private AccountService accountService;

    @GetMapping("manager")
    public String accountHome(Model model){
        List<Account> accountList = accountService.fidnAll();
        model.addAttribute("accountList", accountList);
        return "account/home";
    }

    @GetMapping("new")
    public String newAccount(){
        return "account/new";
    }

    @PostMapping("/new")
    public String saveAccount(Account account){
        accountService.saveNewAccount(account);
        return "redirect:/account/manager";
    }

    @GetMapping("detail/{id:\\d+}")
    public String detail(Model model, @PathVariable Integer id){
        Account account = accountService.findAccountById(id);
        List<Role> roleList = accountService.findRoleListByAccountId(id);
        model.addAttribute("account" ,account);
        System.out.println(account.getAccountName() + ">>>>>>>>>>>>>>>>>>");
        model.addAttribute("roleList",roleList);
        return "/account/detail";
    }

    @GetMapping("edit/{id:\\d+}")
    public String edit(Model model, @PathVariable Integer id){
        model.addAttribute("account",accountService.findAccountById(id));
        model.addAttribute("roleList",accountService.findRoleListByAccountId(id));
        return "/account/edit";
    }
}
