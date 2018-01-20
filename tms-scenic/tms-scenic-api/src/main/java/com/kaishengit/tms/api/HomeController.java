package com.kaishengit.tms.api;

import com.kaishengit.tms.entity.ScenicAccount;
import com.kaishengit.tms.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * @author NativeBoy
 */
@Controller
public class HomeController {

    @Inject
    private AccountService accountService;

    /**
     * 去登录界面
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

    /**
     * 登录认证,权限认证
     * @param mobile
     * @param password
     * @param rememberMe
     * @param redirectAttributes
     * @param request
     * @return
     */
    @PostMapping("/")
    public String home(String mobile, String password, boolean rememberMe , RedirectAttributes redirectAttributes, HttpServletRequest request){
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(mobile, password);
            subject.login(token);

            String ip = request.getRemoteAddr();
            ScenicAccount scenicAccount = (ScenicAccount) subject.getPrincipal();
            accountService.saveWriteLoginLogWithScenicAccount(ip,scenicAccount.getId());

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

    /**
     * 个人设置
     * @return
     */
    @GetMapping("/profile")
    @ResponseBody
    public String onlyOneNoTwo(){
        return "<h1>Get to work! Don't fooling around</h1>";
    }

    @GetMapping("/logo")
    @ResponseBody
    public String logo(){
        return "<h1>This is where the company marks you</h1>";
    }

}
