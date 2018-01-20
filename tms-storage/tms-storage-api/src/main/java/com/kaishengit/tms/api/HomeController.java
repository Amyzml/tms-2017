package com.kaishengit.tms.api;


import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * @author NativeBoy
 */

@Controller
public class HomeController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String index(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            subject.logout();
        }
        if(!subject.isAuthenticated() && subject.isRemembered()){
            return "redirect:/home";
        }
        return "index";
    }

    @PostMapping("/")
    public String login(String mobile, String password, RedirectAttributes redirectAttributes, HttpServletRequest request){
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(mobile, password);
            subject.login(token);
            subject.hasRole("storage");
            String ip = request.getRemoteAddr();
            Account account = (Account) subject.getPrincipal();
            accountService.saveWriteLoginLog(ip,account.getId());

            return "home";
        }catch (AuthenticationException ex){
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message","账号或者密码错误");
            return "redirect:/";
        }catch (AuthorizationException exception){
            exception.printStackTrace();
            redirectAttributes.addAttribute("message","权限不足");
            return "redirect:/";
        }
    }
}
