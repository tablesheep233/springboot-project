package org.table.neweims.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.table.neweims.entities.User;
import org.table.neweims.interceptor.Token;
import org.table.neweims.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
                            @RequestParam("password") String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.userRegister(user);
        return "user/login";
    }

    /**
     * 用户登录
     * 成功后把用户信息放入session
     * 根据用户角色跳转到对应页面，若无角色代表新用户，跳转选择用户类型
     * @param username
     * @param password
     * @param session
     * @return
     */
    @Token(saveToken = true)
    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        subject.login(token);

        User loginUser = userService.getUserInfo(username);
        session.setAttribute("loginUser",loginUser);
        if (subject.hasRole("student")){
            session.setAttribute("role","student");
            return "redirect:/student";
        }else if (subject.hasRole("enterprise")){
            session.setAttribute("role","enterprise");
            return "redirect:/enterprise";
        }else if (subject.hasRole("admin")){
            session.setAttribute("role","admin");
            return "redirect:/admin";
        }else {
            return "redirect:/choose";
        }
    }

    /**
     * 首次注册的用户选择用户类型
     * @param roleName
     * @return
     */
    @Token(removeToken = true)
    @ResponseBody
    @RequestMapping("/loginUser/chooseRole")
    public String chooseRole(
            @RequestParam("role") String roleName,
            HttpSession session){
        Subject subject = SecurityUtils.getSubject();
        userService.setUserRole(roleName);
        if (subject.hasRole("student")){
            session.setAttribute("role","student");
            return "student/main";
        }else {
            session.setAttribute("role","enterprise");
            return "enterprise/main";
        }
    }

    @RequestMapping("/loginUser/upUserInfo")
    public String upUserInfo(User loginUser,
                             HttpSession session){
        session.setAttribute("loginUser",userService.userUpInfo(loginUser));
        String role = (String) session.getAttribute("role");
        return role+"/";
    }

    @RequestMapping("/loginUser/info")
    public String userInfo(){

        return "user/info";
    }
}
