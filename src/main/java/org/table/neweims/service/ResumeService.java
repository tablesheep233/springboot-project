package org.table.neweims.service;

import org.table.neweims.dto.ApplyResume;
import org.table.neweims.dto.Page;
import org.table.neweims.entities.Resume;
import org.table.neweims.enums.StatusEnum;

import java.util.List;
import java.util.Map;

public interface ResumeService {

    List<Map<String,Object>> getResumeList(Integer userId);

    Resume getResume(Integer id);

    void saveResume(Resume resume);

    void deleteResume(Integer id);

    void commitRecruitment(Integer resumeId,Integer recruitmentId);

    Page<Resume> getApplyResume(Integer userId,String search,StatusEnum statusEnum,Integer currPage);

    ApplyResume getApplyResumeById(Integer id);

    List<Map<String,Object>> getDeliveryList();

    void collectResume(Integer id , StatusEnum statusEnum);
}
