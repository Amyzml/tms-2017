package com.kaishengit.tms.api;

import com.google.gson.Gson;
import com.kaishengit.tms.entity.StoreAccount;
import com.kaishengit.tms.entity.TicketStore;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.service.TicketStoreService;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 售票点业务控制器
 * @author NativeBoy
 */
@Controller
@RequestMapping("/ticketStore")
public class TicketStoreController {

    private String ak = "BJSh2yr7jNSyQdBVryDw6mZ-a9frqfp_LRUD2U1T";

    private String sk = "MgKmnIrj9adez2ZQMMrz2H0us4GFbq4a1gnFaaqQ";

    @Autowired
    private TicketStoreService ticketStoreService;

    /**
     * 站点列表
     * @param model
     * @return
     */
    @GetMapping("/message")
    public String ticketStoreList(Model model){
        model.addAttribute("ticketStoreList",ticketStoreService.findAll());
        return "/ticketStore/list";
    }

    /**
     * 新增站点信息
     * @return
     */
    @GetMapping("/new")
    public String ticketStoreNew (){
        return "/ticketStore/new";
    }

    /**
     * 获取站点信息详情
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/detail/{id:\\d+}")
    public String detail(Model model, @PathVariable Integer id){
        model.addAttribute("ticketStore",ticketStoreService.findById(id));
        return "/ticketStore/detail";
    }

    /**
     * 修改站点信息
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/edit/{id:\\d+}")
    public String edit(Model model, @PathVariable Integer id){
        model.addAttribute("ticketStore",ticketStoreService.findById(id));
        return "/ticketStore/edit";
    }

    /**
     * 开通账号 售票点
     * @param id 售票点id
     * @param model
     * @return
     */
    @GetMapping("/newAccount/{id:\\d+}")
    public String newAccount(@PathVariable Integer id,Model model){
        model.addAttribute("ticketStoreId",id);
        return "ticketStore/newAccount";
    }

    /**
     * 保存并绑定账号
     * @return
     */
    @PostMapping("/newAccount/{id:\\d+}")
    public String saveNewAccount(StoreAccount storeAccount,@PathVariable Integer id,
                                 RedirectAttributes redirectAttributes){
        ticketStoreService.saveNewStoreAccount(storeAccount,id);
        redirectAttributes.addFlashAttribute("message","创建站点账户成功");
        return "redirect:/ticketStore/message";
    }

    /**
     * 保存修改
     * @return
     */
    @PostMapping("/edit/{id\\d+}")
    public String update(TicketStore ticketStore){
        return "redirect:/ticketStore/detail/" + ticketStore.getId();
    }

    /**
     * 保存新增站点信息
     * @param ticketStore
     * @param paperImg
     * @param IDcardImg
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/new")
    @Transactional(rollbackFor = RuntimeException.class)
    public String saveTicketStore(TicketStore ticketStore, MultipartFile paperImg ,
                                  MultipartFile IDcardImg,RedirectAttributes redirectAttributes){
        try {
            if(paperImg.isEmpty() || IDcardImg.isEmpty()){
                throw new ServiceException("证件必须上传");
            }
            String paperImgName = uploadToQiNiu(paperImg.getInputStream());
            String IDcardImgName = uploadToQiNiu(IDcardImg.getInputStream());

            ticketStore.setCreateTime(new Date());
            ticketStore.setUpdateTime(new Date());
            ticketStore.setStoreAttachment(paperImgName);
            ticketStore.setStoreManagerAttachment(IDcardImgName);

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message","保存失败");
        }

        ticketStoreService.saveNew(ticketStore);
        return "redirect:/ticketStore/message";
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
