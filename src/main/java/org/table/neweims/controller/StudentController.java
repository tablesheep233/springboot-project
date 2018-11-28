package org.table.neweims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.table.neweims.entities.Student;
import org.table.neweims.service.StudentService;
import org.table.neweims.util.MyResult;

import javax.servlet.http.HttpSession;

@RequestMapping("/student")
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/info")
    public ModelAndView getStuInfo(HttpSession session){
        ModelAndView mv = new ModelAndView();
        Integer userId = (Integer) session.getAttribute("loginUser");
        MyResult result = studentService.getStuInfo(userId);
        if(!result.getTest()){
            mv.setViewName("student/bind");
            return mv;
        } else {
            mv.setViewName("student/info");
            mv.addObject("stuInfo",result.get("result"));
            return mv;
        }
    }

    @RequestMapping("/bindInfo")
    public ModelAndView bindStuInfo(@RequestParam("username") String username,
                               @RequestParam("password") String password){
        MyResult result = studentService.bindStuInfo(username,password);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("student/info");
        mv.addObject("stuInfo",result.get("result"));
        return mv;
    }

    @PutMapping("/student")
    public String putStuInfo(Student student, Model model){
        studentService.setStu(student);
        model.addAttribute("stuInfo",student);
        model.addAttribute("msg","success");
        return "student/info";
    }
}
