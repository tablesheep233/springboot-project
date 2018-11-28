package org.table.neweims.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
}