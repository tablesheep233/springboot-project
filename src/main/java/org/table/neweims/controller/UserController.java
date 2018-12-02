package org.table.neweims.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.table.neweims.entities.User;
import org.table.neweims.interceptor.Token;
import org.table.neweims.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册时检查用户名是否重复
     * 登录时检查用户名是否存在
     * @param username
     * @return
     */
    @RequestMapping("/user/checkUserName")
    @ResponseBody
    public String checkName(@RequestParam String username){
        if (userService.checkUserName(username)){
            return "no";
        }
        return "have";
    }

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/user/register")
    public String register(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                           @RequestParam("confirmPassword") String confirmPassword,
                           @RequestParam("role") String roleName){

        if(!password.equals(confirmPassword)){
            return "redirect:/register";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.userRegister(user);
        userService.setUserRole(username,roleName);
        return "redirect:/login";
    }

    /**
     * 用户登录
     * 成功后把用户id放入session
     * 重定向到主页
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        subject.login(token);

        User loginUser = userService.getUserInfo(username);
        session.setAttribute("loginUser",loginUser.getId());
        return "redirect:/main";
    }
}
