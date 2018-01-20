package com.kaishengit.tms.auth;

import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.entity.Role;
import com.kaishengit.tms.service.AccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NativeBoy
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
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
        //获取对象对应的角色或者权限列表
        List<Role> roleList = accountService.findRoleListByAccountId(account.getId());
        List<String> roleNames = new ArrayList<>();
        for(Role role : roleList){
            roleNames.add(role.getRoleName());
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //将roleNames作为权限认证的角色
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
        if(account != null){
            return new SimpleAuthenticationInfo(account,account.getAccountPassword(),getName());
        }
        return null;
    }
}
