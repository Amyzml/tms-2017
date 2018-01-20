package com.kaishengit.tms.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author NativeBoy
 */
@Controller
@RequestMapping("/leader")
public class LeaderController {

    /**
     * 查询站点信息
     * @return
     */
    @GetMapping("/store/message")
    public String getMessageStore(){
        return "leader/store";
    }

    /**
     * 查询景区信息
     * @return
     */
    @GetMapping("/scenic/message")
    public String getMessageScenic(){
        return "leader/scenic";
    }

    /**
     * 查询年票信息
     * @return
     */
    @GetMapping("/ticket/message")
    public String getMessageTicket(){
        return "leader/ticket";
    }

    /**
     * 特约消费点 信息
     * @return
     */
    @GetMapping("/consumer/message")
    public String getMessageConsumerStore(){
        return "leader/consumerStore";
    }

    @GetMapping("/total/select")
    public String getTotalMessage(){
        return "leader/total";
    }
}


