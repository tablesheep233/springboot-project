package org.table.neweims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.table.neweims.dto.Page;
import org.table.neweims.dto.RecruitmentDto;
import org.table.neweims.entities.Enterprise;
import org.table.neweims.entities.Recruitment;
import org.table.neweims.enums.StatusEnum;
import org.table.neweims.service.EnterpriseService;
import org.table.neweims.service.RecruitmentService;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class RecruitmentController {

    @Autowired
    private RecruitmentService recruitmentService;

    @Autowired
    private EnterpriseService enterpriseService;

    @GetMapping("/recruitment")
    public String recruitmentList(Integer currPage,String search, Model model){
        currPage = currPage==null?1:currPage;
        if (search==null||search.equals("")){
            search = null;
        }
        Page<Recruitment> page = recruitmentService.queryRecruitmentByPage(currPage,search);
        model.addAttribute("page",page);
        return "enterprise/recruitment/list";
    }

    @GetMapping("/recruitment/{id}")
    public String getRecruitment(@PathVariable("id") Integer id , Model model){

        Map<String,Object> recruitment = recruitmentService.queryRecruitmentById(id);
        model.addAttribute("recruitment",recruitment);
        return "enterprise/recruitment/recruitment";
    }

    @GetMapping("/toaddrecruitment")
    public String toAdd(Model model, HttpSession session){
        Integer userId = (Integer) session.getAttribute("loginUser");
        Enterprise enterprise = (Enterprise) enterpriseService.getEnterpriseByUser(userId).get("result");
        model.addAttribute("enterprise",enterprise);
        return "enterprise/recruitment/recruitment";
    }

    @PostMapping("/recruitment")
    public String addRecruitment(Recruitment recruitment, RedirectAttributes attr){
        recruitmentService.saveRecruitment(recruitment);
        attr.addFlashAttribute("tip","添加成功");
        return "redirect:/recruitment";
    }

    @DeleteMapping("/recruitment/{id}")
    public String deleteRecruitment(@PathVariable("id") Integer id, String search, RedirectAttributes attr){
        recruitmentService.dropRecruitment(id);
        attr.addFlashAttribute("tip","删除成功");
        attr.addFlashAttribute("search",search);
        return "redirect:/recruitment";
    }

    @PutMapping("/recruitment")
    public String editRecruitment(Recruitment recruitment, RedirectAttributes attr){
        recruitmentService.saveRecruitment(recruitment);
        attr.addFlashAttribute("tip","修改成功");
        return "redirect:/recruitment/"+recruitment.getId();
    }


    @GetMapping("/chooserecruitment/{id}")
    public String getRecruitmentToStu(@PathVariable("id")Integer id, Model model){
        Map<String,Object> recruitment = recruitmentService.queryRecruitmentById(id);
//        recruitmentService.recruitmentStatus(id);
        model.addAttribute("recruitment",recruitment);
        return "enterprise/recruitment/choose";
    }

    @GetMapping("/recruitmentStatus")
    public String getRecruitmentStatus(Integer currPage,String search, Model model){
        currPage = currPage==null?1:currPage;
        Page<RecruitmentDto> page = recruitmentService.queryRecruitmentStatusByPage(currPage,search);
        model.addAttribute("status", StatusEnum.values());
        model.addAttribute("page",page);
        return "enterprise/recruitment/alist";
    }

    @ResponseBody
    @GetMapping("/getSr/{id}")
    public Map<String, Object> getSr(@PathVariable("id") Integer id){
        return recruitmentService.queryRecruitmentById(id);
    }
}
