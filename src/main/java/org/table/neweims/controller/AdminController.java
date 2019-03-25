package org.table.neweims.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.table.neweims.dto.EnterpriseDto;
import org.table.neweims.dto.Page;
import org.table.neweims.dto.RecruitmentDto;
import org.table.neweims.entities.Enterprise;
import org.table.neweims.entities.Recruitment;
import org.table.neweims.entities.Student;
import org.table.neweims.entities.Track;
import org.table.neweims.enums.StatusEnum;
import org.table.neweims.service.*;
import org.table.neweims.util.CardInfo;
import org.table.neweims.util.SysContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresPermissions("admin:*")
@Controller
public class AdminController {

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private RecruitmentService recruitmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TrackService trackService;

    @Autowired
    private CardInfo cardInfo;

    @GetMapping("/allenterprise")
    public String getAllEnterprise(Model model, StatusEnum statu,String search,Integer currPage){
        currPage = currPage==null?1:currPage;
        Page<EnterpriseDto> page = enterpriseService.getAllEnterprise(statu,search,currPage);
        StatusEnum[] enums = {StatusEnum.WAIT,StatusEnum.REAL,StatusEnum.UNREAL};
        model.addAttribute("status",enums);
        model.addAttribute("page",page);
        return "enterprise/list";
    }

    @ResponseBody
    @GetMapping("/getEnterprise/{id}")
    public Enterprise getEnterprise(@PathVariable("id") Integer id){
        return enterpriseService.getEnterpriseById(id);
    }

    @ResponseBody
    @PostMapping("/upStatus")
    public Map<String,Object> upEnterpriseStatus(@RequestParam("enterpriseId") Integer enterpriseId, @RequestParam("status") StatusEnum status){
        Enterprise enterprise = new Enterprise();
        enterprise.setId(enterpriseId);
        enterprise.setStatus(status);
        enterprise.setReviewer(SysContext.getCurrentUser());
        enterpriseService.editEnterprise(enterprise);
        Map<String,Object> map = new HashMap<>();
        map.put("result","success");
        map.put("user",userService.getUserInfo(SysContext.getCurrentUser()).getUsername());
        return map;
    }

    @ResponseBody
    @PostMapping("/upSr")
    public Map<String,Object> upSr(@RequestParam("recruitmentId") Integer recruitmentId, @RequestParam("status") StatusEnum status){
        Recruitment recruitment = new Recruitment();
        recruitment.setId(recruitmentId);
        recruitment.setStatus(status);
        recruitment.setReviewer(SysContext.getCurrentUser());
        recruitmentService.saveRecruitment(recruitment);
        Map<String,Object> map = new HashMap<>();
        map.put("result","success");
        map.put("user",userService.getUserInfo(SysContext.getCurrentUser()).getUsername());
        return map;
    }

    @GetMapping("/stuList")
    public String getStuList(Model model,String name,@ModelAttribute("major") String major,@ModelAttribute("clazz") String clazz,Integer currPage){
        currPage = currPage==null?1:currPage;
        Page<Student> page = studentService.getStuList(name,major,clazz,currPage);
        model.addAttribute("page",page);
        model.addAttribute("mList",studentService.getMajorList());
        model.addAttribute("cList",studentService.getClazzList());
        return "student/list";
    }

    @GetMapping("/getStu/{id}")
    public String getStu(@PathVariable("id")Integer id, Model model){
        Student student = studentService.getStu(id);
        List<Track> tracks = trackService.getTrackListFromStu(id);
        Map<String,Object> map = new HashMap<>();
        map.put("stu",student);
        map.put("tracks",tracks);
        model.addAttribute("map",map);
        return "admin/stu";
    }

    @GetMapping("/rep")
    public String rep(Model model){
        model.addAttribute("sdata",trackService.getSerachData());
        return "admin/sturep";
    }

    @ResponseBody
    @PostMapping("/city")
    public Map<String,Object> city(String year,Integer session,String major){
        Map<String,Object> map = trackService.getCityData(year,session,major);
        return map;
    }

    @ResponseBody
    @PostMapping("/industry")
    public List<Map<String, Object>> industry(String year,Integer session,String major){
        return trackService.getIndustry(year,session,major);
    }

    @ResponseBody
    @PostMapping("/money")
    public List<Map<String, Object>> money(String year,Integer session,String major){
        return trackService.getMoney(year,session,major);
    }

    @RequiresPermissions(value = {"admin:*","ent:legal"},logical = Logical.OR)
    @GetMapping("/recruitmentStatus")
    public String getRecruitmentStatus(Integer currPage,String search, Model model){
        currPage = currPage==null?1:currPage;
        Page<RecruitmentDto> page = recruitmentService.queryRecruitmentStatusByPage(currPage,search);
        StatusEnum[] enums = {StatusEnum.WAIT,StatusEnum.REAL,StatusEnum.UNREAL};
        model.addAttribute("status",enums);
        model.addAttribute("page",page);
        return "enterprise/recruitment/alist";
    }

    @RequiresPermissions("student:*")
    @ResponseBody
    @GetMapping("/getSr/{id}")
    public Map<String, Object> getSr(@PathVariable("id") Integer id){
        Map<String,Object> recruitment = recruitmentService.queryRecruitmentById(id);
        recruitment.put("requirement",cardInfo.resumeReplace(recruitment.get("requirement").toString()));
        return recruitment;
    }
}
