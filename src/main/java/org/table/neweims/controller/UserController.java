package org.table.neweims.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.table.neweims.entities.User;
import org.table.neweims.enums.StatusEnum;
import org.table.neweims.exception.MyException;
import org.table.neweims.interceptor.Token;
import org.table.neweims.service.EnterpriseService;
import org.table.neweims.service.RecruitmentService;
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

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private RecruitmentService recruitmentService;

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
                        HttpSession session) throws MyException{
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        try{
            subject.login(token);
        }catch (Exception e){
            throw new MyException("密码错误",e);
        }


        User loginUser = userService.getUserInfo(username);
        session.setAttribute("loginUser",loginUser.getId());
        return "redirect:/main";
    }

    @RequestMapping("/main")
    public String main(HttpSession session,Model model){
        String role = userService.getUserRole(userService.getUserInfo((Integer) session.getAttribute("loginUser")).getUsername());
        if (role.equals("admin")){
            model.addAttribute("entNum",enterpriseService.getEnterpriseCountBy(StatusEnum.WAIT));
            model.addAttribute("reNum",recruitmentService.getRecruitmentCountByStatus(StatusEnum.WAIT));
        }
        return "user/main";
    }

    @ResponseBody
    @PostMapping("/user/outSms")
    public String outSms(@RequestParam("uphone") String uPhone,@RequestParam("reset") Integer reset,HttpSession session){
        String phoneNum[] = {uPhone};
        String smsText = null;
        try {
            if (reset!=null&&reset==1){
                smsText = verificationCode.outRSms(phoneNum);
            }else{
                smsText = verificationCode.outSms(phoneNum);
            }
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

    @PostMapping("/user/plogin")
    public String plogin(@RequestParam("uphone") String uPhone,HttpSession session){

        String username = userService.getU(uPhone);
        String password = userService.getP(uPhone);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        subject.login(token);

        User loginUser = userService.getUserInfo(username);
        session.setAttribute("loginUser",loginUser.getId());
        return "redirect:/main";
    }

    @PostMapping("/user/reset")
    public String reset(@RequestParam("uphone") String uPhone, @RequestParam("password") String password,
                        @RequestParam("confirmPassword") String confirmPassword, RedirectAttributes attr){
        System.out.println(2333);
        if(!password.equals(confirmPassword)){
            return "redirect:/reset";
        }
        userService.reset(uPhone,password);
        attr.addFlashAttribute("tip","密码重置成功!请登录");
        return "redirect:/msg";
    }
}
