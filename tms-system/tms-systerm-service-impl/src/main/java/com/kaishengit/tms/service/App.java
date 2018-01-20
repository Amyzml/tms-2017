package com.kaishengit.tms.service;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author NativeBoy
 */
public class App {

    private static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath*:spring-context.xml");
        classPathXmlApplicationContext.start();
        logger.info("TMS-System-Service start success....");
        System.in.read();
    }
}
