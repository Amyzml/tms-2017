package com.kaishengit.tms.auth;

import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.entity.Role;
import com.kaishengit.tms.service.AccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NativeBoy
 */
@Repository
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

        //获取当前登录对象
        Account account = (Account) principalCollection.getPrimaryPrincipal();
        List<Role> roleList = accountService.findRoleListByAccountId(account.getId());
        List<String> roleNames = new ArrayList<>();
        for(Role role : roleList){
            roleNames.add(role.getRoleName());
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roleNames);

        return simpleAuthorizationInfo;
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
        Account account = accountService.findByUsername(token.getUsername());
        System.out.println(account.getAccountName());
        if(account != null){
            return new SimpleAuthenticationInfo(account,account.getAccountPassword(),getName());
        }
        return null;
    }
}
