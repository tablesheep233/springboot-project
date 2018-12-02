package org.table.neweims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.table.neweims.dto.Page;
import org.table.neweims.entities.Recruitment;
import org.table.neweims.service.RecruitmentService;

@Controller
public class RecruitmentController {

    @Autowired
    private RecruitmentService recruitmentService;

    @GetMapping("/recruitment")
    public String recruitmentList(Integer currPage){
        currPage = currPage==null?1:currPage;
        Page<Recruitment> page = recruitmentService.queryStudentsByPage(currPage);
        return "";
    }
}
