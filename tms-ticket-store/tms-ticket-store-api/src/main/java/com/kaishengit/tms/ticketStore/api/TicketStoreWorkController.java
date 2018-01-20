package com.kaishengit.tms.ticketStore.api;

import com.google.gson.Gson;
import com.kaishengit.tms.entity.Customer;
import com.kaishengit.tms.entity.StoreAccount;
import com.kaishengit.tms.entity.Ticket;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.service.StorageService;
import com.kaishengit.tms.service.TicketOrderService;
import com.kaishengit.tms.service.TicketService;
import com.kaishengit.tms.util.ResultJson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 售票站点业务控制器
 * @author NativeBoy
 */
@Controller
@RequestMapping("/ticket/store")
public class TicketStoreWorkController {

    private String ak = "BJSh2yr7jNSyQdBVryDw6mZ-a9frqfp_LRUD2U1T";
    private String sk = "MgKmnIrj9adez2ZQMMrz2H0us4GFbq4a1gnFaaqQ";
    @Autowired
    private StorageService storageService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private TicketOrderService ticketOrderService;


    /**
     * 展示本站点卖出的年票列表
     * @param model
     * @return
     */
    @GetMapping("/showList")
    public String showList(Model model){
        List<Ticket> ticketList = ticketService.findAllTicketLocalSall(getCurrAccount().getId());
        model.addAttribute("ticketList",ticketList);
        return "store/list";
    }
    /**
     * 获取当前登陆对象
     * @return
     */
    private StoreAccount getCurrAccount(){
        Subject subject = SecurityUtils.getSubject();
        StoreAccount storeAccount = (StoreAccount) subject.getPrincipal();
        return storeAccount;
    }
    /**
     * 开户界面 办理年票
     * @param model
     * @return
     */
    @GetMapping("/transaction")
    public String newTicket(Model model){
        List<Ticket> ticketList = storageService.findTicketByStoreId(getCurrAccount().getId());
        model.addAttribute("ticketList",ticketList);
        return "store/new";
    }

    /**
     * 根据年票卡号获取详细信息
     * 年票续费
     * @return
     */
    @GetMapping("/renew")
    public String renew(){
        return "renew/renew";
    }

    /**
     *
     * @param ticketNum 年票卡号
     * @param model 客户信息 年票信息
     * @return
     */
    @GetMapping("/renew/detail")
    public String showMessage(@RequestParam String ticketNum,Model model){
        Ticket ticket = ticketService.findTicketByTicketNum(ticketNum);
        Customer customer = ticketService.findCustomerByTicketId(ticket.getId());
        model.addAttribute("ticket",ticket);
        model.addAttribute("customer",customer);
        return "renew/detail";
    }

    /**
     * 保存续费信息
     * @param ticketId
     * @return
     */
    @GetMapping("/ticketRenew")
    @ResponseBody
    public ResultJson saveRenewMessage(@RequestParam Integer ticketId){
        try {
            ticketService.saveRenewWithTicketId(ticketId);
            return ResultJson.success();
        }catch (ServiceException ex){
            return ResultJson.error("请输入有效的卡号");
        }
    }

    /**
     * 访问补办界面
     * @return
     */
    @GetMapping("/reapply")
    public String reapply(){
        return "renew/reapply";
    }

    /**
     * 补办年票
     * @param IDcard 身份证
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/reapply")
    public String saveReapply(String IDcard,RedirectAttributes redirectAttributes){
        Customer customer = ticketService.findCustomerByIDcard(IDcard);
        if(customer == null){
            return "redirect:/ticket/store/error";
        }
        try {
            ticketService.reapplyTicketBecauseCardLoss(customer.getTicketId());
            //redirectAttributes.addFlashAttribute("message", "补办成功");
            return "redirect:/ticket/store/reapplyNewCard/" + customer.getTicketId();
        }catch (ServiceException ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/ticket/store/sayLoss";
        }
    }

    /**
     * 跳转到补办界面,需要新的年票列表信息
     * @param id 需要补办的年票id
     * @param model
     * @return
     */
    @GetMapping("/reapplyNewCard/{id:\\d+}")
    public String reapplyNewCard(@PathVariable Integer id,Model model){
        //当前站点有效年票列表
        List<Ticket> ticketList = storageService.findTicketByStoreId(getCurrAccount().getId());
        model.addAttribute("ticketList",ticketList);
        return "renew/reapplyNewCard";
    }

    /**
     * 补办新卡
     * @param id old年卡id
     * @param redirectAttributes
     * @param ticketId 新卡
     * @return
     */
    @PostMapping("/reapplyNewCard/{id:\\d+}")
    public String saveReapplyNewCard(@PathVariable Integer id,RedirectAttributes redirectAttributes,
                                     String ticketId){
        try {
            ticketService.saveOldCardCoppyToNewTicket(id, ticketId);

            Customer customer = ticketService.findCustomerByTicketId(Integer.valueOf(ticketId));

            ticketOrderService.saveTicketOrderBecauseOldToNew(ticketId,getCurrAccount().getId(),customer.getId());

            redirectAttributes.addFlashAttribute("message","补办成功");
            return "redirect:/ticket/store/detail/" + customer.getId();
        }catch (ServiceException ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/ticket/store/reapply";
        }
    }
    /**
     * 访问更换年票界面
     * @return
     */
    @GetMapping("/replace")
    public String replace(){
        return "renew/replace";
    }

    /**
     * 访问办理统计界面
     * @return
     */
    @GetMapping("/server")
    public String server(Model model){
        List<Ticket> ticketList = ticketService.findAllTicketLocalSall(getCurrAccount().getId());
        model.addAttribute("ticketList",ticketList);
        return "renew/server";
    }
    /**
     * 访问挂失界面
     * @return
     */
    @GetMapping("/sayLoss")
    public String sayLoss(){
        return "renew/loss";
    }

    /**
     * 根据身份证号码进行挂失
     * @param IDcard 身份号码
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/sayLoss")
    public String saveLossMessage(String IDcard,RedirectAttributes redirectAttributes){
        Customer customer = ticketService.findCustomerByIDcard(IDcard);
        if(customer == null){
            return "redirect:/ticket/store/error";
        }
        try {
            ticketService.updateTicketBecauseCardLoss(customer.getTicketId());
            redirectAttributes.addFlashAttribute("message", "挂失成功");
            return "redirect:/ticket/store/sayLoss";
        }catch (ServiceException ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/ticket/store/sayLoss";
        }
    }

    /**
     * 访问解卦界面
     * @return
     */
    @GetMapping("/sayFind")
    public String sayFind(){
        return "renew/find";
    }

    /**
     * 解卦
     * @param IdCard 身份号码
     * @return
     */
    @PostMapping("/sayFind")
    public String saveSayFind(String IdCard,RedirectAttributes redirectAttributes){
        Customer customer = ticketService.findCustomerByIDcard(IdCard);
        if(customer == null){
            return "redirect:/ticket/store/error";
        }
        try {
            ticketService.updateTicketBecauseCardFind(customer.getTicketId());
            redirectAttributes.addFlashAttribute("message", "解挂成功");
            return "redirect:/ticket/store/sayFind";
        }catch (ServiceException ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/ticket/store/sayFind";
        }
    }

    /**
     * 错误界面
     * @return
     */
    @GetMapping("/error")
    public String errorMessage(){
        return "error";
    }

    /**
     * 保存年票办理信息,并绑定顾客信息与对应的年票
     * @param customer 顾客
     * @return
     */
    @PostMapping("/transaction")
    @Transactional(rollbackFor = RuntimeException.class)
    public String saveNewTicket(Customer customer,
                                MultipartFile truePhoto,
                                MultipartFile falsePhoto,
                                MultipartFile peoplePhoto,
                                RedirectAttributes redirectAttributes){
        if(truePhoto.isEmpty() || falsePhoto.isEmpty() || peoplePhoto.isEmpty()){
            throw new ServiceException("证件信息或者照片必须上传");
        }
        try {
            String trueName = uploadToQiNiu(truePhoto.getInputStream());
            String falseName = uploadToQiNiu(falsePhoto.getInputStream());
            String peopleName = uploadToQiNiu(peoplePhoto.getInputStream());

            customer.setCustomerIdCardPhoto(trueName);
            customer.setCustomerIdCardPhotoBack(falseName);
            customer.setCustomerPhoto(peopleName);

            ticketService.saveCustomer(customer);
            //只是为了获取insert后的id
            Customer customer1 = ticketService.findCustomerByTicketId(customer.getTicketId());
            //添加一条年票订单数据
            ticketOrderService.saveNewTicketOrder(customer1.getId(),customer1.getTicketId(),getCurrAccount().getId());
            redirectAttributes.addFlashAttribute("message","办理成功");
            return "redirect:/ticket/store/detail/" + customer1.getId();
        }catch (IOException ex){
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message","保存失败");
        }
        return "redirect:/ticket/store/transaction";
    }

    /**
     * 查看办理年票成功后的详情页面,包括顾客详情和年票信息
     * id 顾客id
     * @return
     */
    @GetMapping("/detail/{id:\\d+}")
    public String detail(Model model, @PathVariable Integer id){
        Customer customer = ticketService.findCustomerById(id);
        Ticket ticket = ticketService.findTicketByCustomerId(id);
        model.addAttribute("ticket",ticket);
        model.addAttribute("customer",customer);
        return "store/detail";
    }

    /**
     * 上传文件到七牛
     * @return
     */
    private String uploadToQiNiu(InputStream inputStream){
        Configuration configuration = new Configuration(Zone.zone1());
        UploadManager uploadManager = new UploadManager(configuration);

        Auth auth = Auth.create(ak,sk);
        String uploadToken = auth.uploadToken("test001");

        try {
            Response response = uploadManager.put(IOUtils.toByteArray(inputStream),null,uploadToken);
            DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
            return defaultPutRet.key;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("上传文件到七牛异常", e);
        }
    }

}
