package org.table.neweims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.table.neweims.dto.ApplyResume;
import org.table.neweims.dto.Page;
import org.table.neweims.entities.Resume;
import org.table.neweims.enums.StatusEnum;
import org.table.neweims.service.ResumeService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ApplyController {

    @Autowired
    private ResumeService resumeService;

    @GetMapping("/apply")
    public String applyList(Integer currPage ,String search,StatusEnum statu,Model model, HttpSession session){
        currPage = currPage==null?1:currPage;
        if (search==null||search.equals("")){
            search = null;
        }
        if (statu==null||statu.getMessage().equals("")){
            statu=null;
        }
        Integer userId = (Integer) session.getAttribute("loginUser");
        Page<Resume> page = resumeService.getApplyResume(userId,search,statu,currPage);
        model.addAttribute("page",page);
        StatusEnum[] enums = {StatusEnum.READ,StatusEnum.NOTREAD,StatusEnum.COLLECT};
        model.addAttribute("status",enums);
        model.addAttribute("statu",statu);
        return "enterprise/recruitment/apply";
    }

    @ResponseBody
    @GetMapping("/applyResume/{id}")
    public ApplyResume getApplyResumeById(@PathVariable("id")Integer id){
        ApplyResume applyResume = resumeService.getApplyResumeById(id);
        return applyResume;
    }

    @ResponseBody
    @PostMapping("/collectResume")
    public Map<String,String> collect(@RequestParam("id")Integer id,@RequestParam("status") StatusEnum status){
        resumeService.collectResume(id,status);
        Map<String,String> map = new HashMap<>();
        map.put("msg","操作成功");
        return map;
    }
}
