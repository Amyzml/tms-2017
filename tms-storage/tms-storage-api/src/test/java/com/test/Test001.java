package com.test;

import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author NativeBoy
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-dubbo.xml")
public class Test001 {


    @Autowired
    private AccountService accountService;


    @Test
    public void gg() throws IOException {

        Account account = accountService.findByUsername("182");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>" + account.getAccountName());
        System.in.read();
    }
}
