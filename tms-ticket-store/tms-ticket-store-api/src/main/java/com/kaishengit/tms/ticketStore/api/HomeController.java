package com.kaishengit.tms.ticketStore.api;

import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.entity.StoreAccount;
import com.kaishengit.tms.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author NativeBoy
 */
@Controller
public class HomeController {

    @Autowired
    private AccountService accountService;

    /**
     * 访问登录页面
     * @return
     */
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
    public String home(String mobile, String password, boolean rememberMe , RedirectAttributes redirectAttributes, HttpServletRequest request){
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(mobile, password, rememberMe);
            subject.login(token);
            //跳转到登陆前输入的url
            String url = "/home";
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            if(savedRequest != null){
                //获取登陆前输入的url
                url = savedRequest.getRequestUrl();
            }
            //登录成功,记录日志
            String ip = request.getRemoteAddr();
            StoreAccount storeAccount = (StoreAccount) subject.getPrincipal();
            accountService.saveWriteLoginLogWithStoreAccount(ip,storeAccount.getId());

            return "redirect:" + url;
        }catch (AuthenticationException ex){
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message","账号或者密码错误");
            return "redirect:/";
        }
    }

    /**
     * 去首页
     * @return
     */
    @GetMapping("/home")
    public String home(){
        return "home";
    }

    /**
     * 安全退出
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes){

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        redirectAttributes.addFlashAttribute("message","您已安全退出系统");
        return "redirect:/";
    }
}
