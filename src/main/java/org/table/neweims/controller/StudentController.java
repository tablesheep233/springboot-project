package org.table.neweims.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.table.neweims.dto.Page;
import org.table.neweims.entities.Student;
import org.table.neweims.exception.MyException;
import org.table.neweims.service.StudentService;
import org.table.neweims.util.MyResult;

import javax.servlet.http.HttpSession;

@RequiresPermissions("student:*")
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/student")
    public String getStuInfo(HttpSession session,Model model){
        Integer userId = (Integer) session.getAttribute("loginUser");
        MyResult result = studentService.getStuInfo(userId);
        if(!result.getTest()){
            return "student/bind";
        } else {
            model.addAttribute("stuInfo",result.get("result"));
            return "student/info";
        }
    }

    @PostMapping("/student")
    public String bindStuInfo(@RequestParam("username") String username,
                               @RequestParam("password") String password,Model model) throws MyException {
        MyResult result = null;
        try {
            result = studentService.bindStuInfo(username,password);
        }catch (MyException e){
            throw e;
        }
        model.addAttribute("stuInfo",result.get("result"));
        return "student/info";
    }

    @PutMapping("/student")
    public String putStuInfo(Student student, Model model){
        studentService.setStu(student);
        model.addAttribute("stuInfo",student);
        model.addAttribute("msg","success");
        return "student/info";
    }


}
