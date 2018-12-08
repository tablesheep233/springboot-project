package org.table.neweims.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.table.neweims.entities.Resume;
import org.table.neweims.service.ResumeService;
import org.table.neweims.service.StudentService;
import org.table.neweims.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RequiresPermissions("resume:*")
@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/resume")
    public String resumeList(Model model,HttpSession session){

        List<Map<String,Object>> list = resumeService.getResumeList((Integer) session.getAttribute("loginUser"));
        model.addAttribute("resumeList",list);
        return "student/resume/resumelist";
    }

    @GetMapping("/resume/{id}")
    public String resumeDetail(@PathVariable("id") Integer id, Model model,HttpSession session){
        Integer userId = (Integer) session.getAttribute("loginUser");
        model.addAttribute("stu",studentService.getStuInfo(userId).get("result"));
        model.addAttribute("resume",resumeService.getResume(id));
        return "student/resume/resume";
    }

    @PutMapping("/resume")
    public String editResume(Resume resume, RedirectAttributes attr){
        resumeService.saveResume(resume);
        attr.addFlashAttribute("tip","修改成功");
        return "redirect:/resume";
    }

    @GetMapping("/toaddresume")
    public String toAddResume(Model model,HttpSession session){
        Integer userId = (Integer) session.getAttribute("loginUser");
        model.addAttribute("stu",studentService.getStuInfo(userId).get("result"));
        return "student/resume/resume";
    }

    @DeleteMapping("/resume/{id}")
    public String deleteResume(@PathVariable("id") Integer id, RedirectAttributes attr){
        resumeService.deleteResume(id);
        attr.addFlashAttribute("tip","删除成功");
        return "redirect:/resume";
    }

    @PostMapping("/resume")
    public String addResume(Resume resume, RedirectAttributes attr){
        resumeService.saveResume(resume);
        attr.addFlashAttribute("tip","添加成功");
        return "redirect:/resume";
    }
}
