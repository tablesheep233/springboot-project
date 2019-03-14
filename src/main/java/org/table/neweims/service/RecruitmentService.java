package org.table.neweims.service;

import org.table.neweims.dto.Page;
import org.table.neweims.dto.RecruitmentDto;
import org.table.neweims.entities.Recruitment;
import org.table.neweims.enums.StatusEnum;

import java.util.List;
import java.util.Map;


public interface RecruitmentService {

    Page<Recruitment> queryRecruitmentByPage(int currPage,String name);

    Map<String,Object> queryRecruitmentById(Integer id);

    void saveRecruitment(Recruitment recruitment);

    void dropRecruitment(Integer id);

    void recruitmentStatus(Integer id);

    Page<RecruitmentDto> queryRecruitmentStatusByPage(int currPage, String name);

    Integer getRecruitmentCountByStatus(StatusEnum statusEnum);

    List<Map<String,Object>> getSSData(String date,Integer userId);
}
