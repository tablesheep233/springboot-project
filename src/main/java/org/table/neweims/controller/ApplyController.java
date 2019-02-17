package org.table.neweims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.table.neweims.dto.ApplyResume;
import org.table.neweims.dto.Page;
import org.table.neweims.entities.Resume;
import org.table.neweims.service.ResumeService;

import javax.servlet.http.HttpSession;

@Controller
public class ApplyController {

    @Autowired
    private ResumeService resumeService;

    @GetMapping("/apply")
    public String applyList(Integer currPage ,String search,Model model, HttpSession session){
        currPage = currPage==null?1:currPage;
        if (search==null||search.equals("")){
            search = null;
        }
        Integer userId = (Integer) session.getAttribute("loginUser");
        Page<Resume> page = resumeService.getApplyResume(userId,search,currPage);
        model.addAttribute("page",page);

        return "enterprise/recruitment/apply";
    }

    @ResponseBody
    @GetMapping("/applyResume/{id}")
    public ApplyResume getApplyResumeById(@PathVariable("id")Integer id){
        ApplyResume applyResume = resumeService.getApplyResumeById(id);
        return applyResume;
    }
}
