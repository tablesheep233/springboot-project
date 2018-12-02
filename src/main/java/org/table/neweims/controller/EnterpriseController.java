package org.table.neweims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.table.neweims.entities.Enterprise;
import org.table.neweims.service.EnterpriseService;
import org.table.neweims.util.MyResult;

import javax.servlet.http.HttpSession;

@RequestMapping("/enterprise")
@Controller
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @GetMapping
    public String toAddEnterprise(Model model, HttpSession session){

        MyResult result = enterpriseService.getEnterpriseByUser((Integer) session.getAttribute("loginUser"));
        if (result.getTest()){
            model.addAttribute("enterprise",result.get("result"));
        }
        return "enterprise/info";
    }

    @PostMapping
    public String addEnterprise(Enterprise enterprise){
        enterpriseService.addEnterprise(enterprise);
        return "enterprise/main";
    }

    @PutMapping
    public String editEnterprise(Enterprise enterprise){
        enterpriseService.editEnterprise(enterprise);
        return "enterprise/main";
    }
}
