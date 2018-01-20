package com.kaishengit.tms.api;

import com.google.gson.Gson;
import com.kaishengit.tms.entity.Scenic;
import com.kaishengit.tms.entity.ScenicAccount;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.service.ScenicService;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.List;

/**
 * 景区业务控制器
 * @author NativeBoy
 */
@Controller
@RequestMapping("/scenic")
public class ScenicController {


    private String ak = "BJSh2yr7jNSyQdBVryDw6mZ-a9frqfp_LRUD2U1T";

    private String sk = "MgKmnIrj9adez2ZQMMrz2H0us4GFbq4a1gnFaaqQ";

    @Autowired
    private ScenicService scenicService;

    /**
     * 获取景区信息列表
     * @param model
     * @return
     */
    @GetMapping("/message")
    public String scenicList(Model model){
        List<Scenic> scenicList = scenicService.findAll();
        model.addAttribute("scenicList",scenicList);
        return "/scenic/list";
    }

    /**
     * 景区详细信息介绍
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/detail/{id:\\d+}")
    public String detail(@PathVariable Integer id, Model model){
        Scenic scenic = scenicService.findById(id);
        model.addAttribute("scenic",scenic);
        return "/scenic/detail";
    }

    /**
     * 编辑景区信息
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/edit/{id:\\d+}")
    public String edit(Model model, @PathVariable Integer id){
        Scenic scenic = scenicService.findById(id);
        model.addAttribute("scenic",scenic);
        return "/scenic/edit";
    }

    /**
     * 保存修改
     * @param scenic
     * @param redirectAttributes
     * @param id
     * @return
     */
    @PostMapping("/edit/{id:\\d+}")
    public String updateScenic(Scenic scenic, RedirectAttributes redirectAttributes,@PathVariable Integer id){
        scenicService.updateScenic(scenic);
        redirectAttributes.addFlashAttribute("message","更新成功");
        return "redirect:/scenic/detail/" + scenic.getId();
    }

    /**
     * 开通账号
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/newAccount/{id:\\d+}")
    public String newScenicAccount(@PathVariable Integer id,Model model){
        model.addAttribute("scenicId",id);
        return "scenic/newAccount";
    }

    /**
     * 保存景区账户并绑定所属景区
     * @return
     */
    @PostMapping("/newAccount/{id:\\d+}")
    public String saveAccount(ScenicAccount scenicAccount,@PathVariable Integer id,
                              RedirectAttributes redirectAttributes){
        scenicService.saveScenicAccount(scenicAccount,id);
        redirectAttributes.addFlashAttribute("message","景区账户创建成功");
        return "redirect:/scenic/message";
    }


    /**
     * 新增景区
     * @return
     */
    @GetMapping("/new")
    public String scenicNew(){
        return "/scenic/new";
    }

    /**
     * 保存新增景区信息,附件上传至七牛云
     * @param scenic 景区
     * @param scenicFile 附件 营业执照
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/new")
    @Transactional(rollbackFor = RuntimeException.class)
    public String saveScenic(Scenic scenic, MultipartFile scenicFile, RedirectAttributes redirectAttributes){
        try {
            if(scenicFile.isEmpty()){
                throw new ServiceException("附件不可为空");
            }
            String filename = uploadToQiNiu(scenicFile.getInputStream());
            scenic.setScenicAttachment(filename);
            scenic.setCreateTime(new Date());
            scenic.setUpdateTime(new Date());
            scenicService.saveScenic(scenic);
            redirectAttributes.addFlashAttribute("message","保存成功");
            return "redirect:/scenic/message";
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message","保存失败");
        }
            return "redirect:/scenic/new";
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
