package org.table.neweims.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.table.neweims.entities.Enterprise;
import org.table.neweims.service.EnterpriseService;
import org.table.neweims.util.MyResult;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiresPermissions("enterprise:*")
@Controller
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @GetMapping("/enterprise")
    public String toAddEnterprise(Model model, HttpSession session){

        MyResult result = enterpriseService.getEnterpriseByUser((Integer) session.getAttribute("loginUser"));
        if (result.getTest()){
            model.addAttribute("enterprise",result.get("result"));
        }
        return "enterprise/info";
    }

    @PostMapping("/enterprise")
    public String addEnterprise(Enterprise enterprise){
        enterpriseService.addEnterprise(enterprise);
        return "redirect:/main";
    }

    @PutMapping("/enterprise")
    public String editEnterprise(Enterprise enterprise){
        enterpriseService.editEnterprise(enterprise);
        return "redirect:/main";
    }

    @RequestMapping("/license")
    public String license(){
        return "enterprise/license";
    }

    @ResponseBody
    @RequestMapping("/uploadlicense")
    public Map<String,Object> upload(@RequestParam("uploadImgFile") MultipartFile uploadImgFile) throws IOException {
        Map<String,Object> map =  enterpriseService.upload(uploadImgFile);
        return map;
    }
}
