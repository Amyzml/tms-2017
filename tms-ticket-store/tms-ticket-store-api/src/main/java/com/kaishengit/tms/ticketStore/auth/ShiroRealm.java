package com.kaishengit.tms.ticketStore.auth;

import com.kaishengit.tms.entity.StoreAccount;
import com.kaishengit.tms.service.AccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author NativeBoy
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private AccountService accountService;

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
        StoreAccount storeAccount = accountService.findStoreAccountByUsername(token.getUsername());
        if(storeAccount != null){
            return new SimpleAuthenticationInfo(storeAccount,storeAccount.getStorePassword(),getName());
        }
        return null;
    }
}
