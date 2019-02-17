package org.table.neweims.mapper;


import org.apache.ibatis.annotations.*;
import org.table.neweims.dto.ApplyResume;
import org.table.neweims.entities.Resume;

import java.util.List;
import java.util.Map;

@Mapper
public interface ResumeMapper {

    List<Map<String,Object>> selectListResume(Integer userId);

    @Select("select * from tb_resume where id = #{id}")
    Resume selectResume(Integer id);

    void updateResume(Resume resume);

    void insertResume(Resume resume);

    @Delete("delete from tb_resume where id = #{id}")
    void deleteResume(Integer id);

    @Insert("insert into tb_resume_recruitment (time, resume_id, recruitment_id) values (#{date},#{resumeId},#{recruitmentId})")
    void insertResumeRecruitment(@Param("date") String date, @Param("resumeId") Integer resumeId, @Param("recruitmentId") Integer recruitmentId);

    List<Map<String,Object>> selectApplyResumeByPage(@Param("userId") Integer userId,@Param("search") String search, @Param("data") Map<String,Object> data);

    Integer selectApplyResumeCount(@Param("userId") Integer userId,@Param("search") String search);

    ApplyResume selectApplyResumeById(Integer id);
}