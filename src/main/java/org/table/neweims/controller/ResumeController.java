package org.table.neweims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.table.neweims.entities.Resume;
import org.table.neweims.service.ResumeService;
import org.table.neweims.service.StudentService;
import org.table.neweims.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/resumes")
    public ModelAndView resumeList(HttpSession session){

        List<Map<String,Object>> list = resumeService.getResumeList((Integer) session.getAttribute("loginUser"));
        ModelAndView mv = new ModelAndView();
        mv.addObject("resumeList",list);
        mv.setViewName("student/resume/resumelist");
        return mv;
    }

    @GetMapping("/resume/{id}")
    public String resumeDetail(@PathVariable("id") Integer id, Model model,HttpSession session){
        model.addAttribute("stu",studentService.getStuInfo((Integer) session.getAttribute("loginUser")).get("result"));
        model.addAttribute("resume",resumeService.getResume(id));
        return "student/resume/resume";
    }

    @PutMapping("/resume")
    public String editResume(Resume resume){
        resumeService.saveResume(resume);
        return "redirect:/resume/"+resume.getId();
    }

    @GetMapping("/resume")
    public String toAddResume(Model model,HttpSession session){
        model.addAttribute("stu",studentService.getStuInfo((Integer) session.getAttribute("loginUser")).get("result"));
        return "student/resume/resume";
    }

    @DeleteMapping("/resume/{id}")
    public String deleteResume(@PathVariable("id") Integer id){
        resumeService.deleteResume(id);
        return "redirect:/resumes";
    }

    @PostMapping("/resume")
    public String addResume(Resume resume){
        resumeService.saveResume(resume);
        return "redirect:/resumes";
    }
}