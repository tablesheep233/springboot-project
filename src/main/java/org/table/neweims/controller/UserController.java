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
import org.table.neweims.util.VerificationCode;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationCode verificationCode;

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
     * @return
     */
    @PostMapping("/user/register")
    public String register(User user, @RequestParam("confirmPassword") String confirmPassword,
                           @RequestParam("role") String roleName){

        if(!user.getPassword().equals(confirmPassword)){
            return "redirect:/register";
        }

        userService.userRegister(user);
        userService.setUserRole(user.getUsername(),roleName);
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


    @ResponseBody
    @PostMapping("/user/outSms")
    public String outSms(@RequestParam("uphone") String uPhone,HttpSession session){
        String phoneNum[] = {uPhone};
        String smsText = null;
        try {
            smsText = verificationCode.outSms(phoneNum);
        }catch (Exception e){

        }
        if(smsText!=null){
            session.setAttribute(uPhone,smsText);
            session.setMaxInactiveInterval(10*60);
            return "true";
        }else {
            return "false";
        }
    }

    @ResponseBody
    @PostMapping("/user/checkSms")
    public Map<Object,Object> checkSms(@RequestParam("smscode") String smscode, @RequestParam("uphone") String uPhone, HttpSession session){
        String ucode = null;
        Map<Object,Object> map = new HashMap<>();
        ucode = (String) session.getAttribute(uPhone);
        if (ucode==null){
            map.put("rest",false);
            map.put("tip","验证码已过期");
        }else if (ucode.equals(smscode)){
            map.put("rest",true);
        }else {
            map.put("rest",false);
            map.put("tip","验证码错误");
        }
        return map;
    }

    @ResponseBody
    @PostMapping("/user/checkPhone")
    public boolean checkPhone(@RequestParam("uphone") String phone){
        return userService.checkPhone(phone);
    }
}
