package org.table.neweims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.table.neweims.dto.EnterpriseDto;
import org.table.neweims.dto.Page;
import org.table.neweims.entities.Enterprise;
import org.table.neweims.enums.StatusEnum;
import org.table.neweims.service.EnterpriseService;
import org.table.neweims.service.UserService;
import org.table.neweims.util.SysContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private UserService userService;

    @GetMapping("/allenterprise")
    public String getAllEnterprise(Model model, String status,String search,Integer currPage){
        currPage = currPage==null?1:currPage;
        Page<EnterpriseDto> page = enterpriseService.getAllEnterprise(status,search,currPage);
        model.addAttribute("status",StatusEnum.values());
        model.addAttribute("page",page);
        return "enterprise/list";
    }

    @ResponseBody
    @GetMapping("/getEnterprise/{id}")
    public Enterprise getEnterprise(@PathVariable("id") Integer id){
        return enterpriseService.getEneeterpriseById(id);
    }

    @ResponseBody
    @PostMapping("/upStatus")
    public Map<String,Object> upEnterpriseStatus(@RequestParam("enterpriseId") Integer enterpriseId, @RequestParam("status") StatusEnum status){
        Enterprise enterprise = new Enterprise();
        enterprise.setId(enterpriseId);
        enterprise.setStatus(status);
        enterprise.setReviewer(SysContext.getCurrentUser());
        enterpriseService.editEnterprise(enterprise);
        Map<String,Object> map = new HashMap<>();
        map.put("result","success");
        map.put("user",userService.getUserInfo(SysContext.getCurrentUser()).getUsername());
        return map;
    }
}
