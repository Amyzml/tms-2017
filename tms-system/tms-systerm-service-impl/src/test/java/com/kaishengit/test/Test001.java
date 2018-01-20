package com.kaishengit.test;

import com.kaishengit.tms.entity.Role;
import com.kaishengit.tms.service.AccountService;
import com.kaishengit.tms.service.impl.AccountServiceImpl;
import com.kaishengit.tms.service.mapper.AccountRoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author NativeBoy
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-context.xml")
public class Test001 {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRoleMapper accountRoleMapper;

    @Test
    public void test002(){
        List<Role> roles = accountService.findRoleListByAccountId(1);
        for(Role role : roles){
            System.out.println(role.getRoleName());
        }
    }
}
