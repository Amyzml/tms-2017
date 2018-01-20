package com.kaishengit.tms.api;

import com.kaishengit.tms.entity.Ticket;

import com.kaishengit.tms.entity.TicketStore;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.service.StorageService;
import com.kaishengit.tms.service.TicketStoreService;
import com.kaishengit.tms.util.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 年票管理
 * @author NativeBoy
 */
@Controller
@RequestMapping("/ticket")
public class TicketManageController {


    @Autowired
    private StorageService storageService;
    @Autowired
    private TicketStoreService ticketStoreService;

    /**
     * 年票入库
     * @param model
     * @return
     */
    @GetMapping("/putToStorage")
    public String putToStorage(Model model){
        model.addAttribute("ticketNum",storageService.findMaxTicketNum());
        return "/ticketManage/add";
    }

    /**
     * 保存入库年票信息
     * @param ticketPutNum
     * @param beginNum
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/putToStorage")
    public String saveTicketPut(String ticketPutNum, String beginNum, RedirectAttributes redirectAttributes){
        storageService.putToStorage(Integer.valueOf(ticketPutNum),beginNum);
        redirectAttributes.addFlashAttribute("message","入库成功");
        return "redirect:/ticket/putToStorage";
    }

    /**
     * 作废年票
     * 显示已经作废的年票卡号
     * @param model
     * @return
     */
    @GetMapping("/cancellation")
    public String cancellation(Model model){
        List<Ticket> ticketList = storageService.findListCancellation();
        model.addAttribute("ticketList",ticketList);
        return "/ticketManage/cancellation";
    }

    /**
     * 作废年票登记
     * @param ticketNum 年票卡号
     * @return
     */
    @PostMapping("/cancellation")
    @ResponseBody
    @Transactional(rollbackFor = RuntimeException.class)
    public ResultJson saveEdit(String ticketNum){
        Ticket ticket = storageService.findTicketByTicketNum(ticketNum);
        if(ticket != null){
            storageService.setTicketCancellation(ticket);
            return ResultJson.success();
        }
        return null;
    }

    /**
     * 年票下发
     * @param model 作废年票号码列表,站点列表,当前有效库存
     * @return
     */
    @GetMapping("/issue")
    public String issue(Model model){
        List<TicketStore> ticketStoreList = ticketStoreService.findAll();
        model.addAttribute("ticketStoreList",ticketStoreList);
        return "/ticketManage/issue";
    }

    /**
     * 保存年票下发信息
     * @return
     */
    @GetMapping("/issue/search")
    public String saveIssue(@RequestParam String storeName, Model model){
        List<TicketStore> ticketStoreList = ticketStoreService.findTicketStorageByStoreName(storeName);

        Long canUseNum = storageService.getTicketNumCanUse();
        Integer minId = storageService.findMinId();
        String minTicketNum = storageService.findMinTicketNumById(minId);

        model.addAttribute("ticketList",storageService.findListCancellation());
        model.addAttribute("ticketStoreList",ticketStoreList);
        model.addAttribute("canUseNum",canUseNum);
        model.addAttribute("minTicketNum",minTicketNum);
        return "/ticketManage/newIssue";
    }

    /**
     * 年票下发
     * @param id 站点id
     * @param ticketNumber 下发数量
     * @param minCanUseCardNum 起始卡号
     * @return 执行状态结果
     */
    @PostMapping("/confirmIssue/{id:\\d+}")
    @ResponseBody
    public ResultJson saveIssue(@PathVariable Integer id, String ticketNumber,String minCanUseCardNum){
        try{
            storageService.saveIssueTicket(id,ticketNumber,minCanUseCardNum);
            return ResultJson.successWithData("下发成功");
        }catch (ServiceException ex){
            return ResultJson.error("下发失败");
        }
    }

}
