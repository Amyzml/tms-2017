package com.kaishengit.tms.auth;

import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.entity.Role;
import com.kaishengit.tms.entity.ScenicAccount;
import com.kaishengit.tms.service.AccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


/**
 * 景区验票系统登录验证
 * @author NativeBoy
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Inject
    private AccountService accountService;

    /**
     * 权限认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        ScenicAccount scenicAccount = accountService.findScenicAccountByUsername(token.getUsername());
        if(scenicAccount != null){
            return new SimpleAuthenticationInfo(scenicAccount,scenicAccount.getScenicPassword(),getName());
        }
        return null;
    }
}
