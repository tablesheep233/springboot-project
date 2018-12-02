package org.table.neweims.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.table.neweims.entities.Recruitment;

import java.util.List;
import java.util.Map;

@Mapper
public interface RecruitmentMapper {

    List<Recruitment> getAllRecruitmentByPage(@Param("user_id") Integer userId, @Param("data") Map<String,Object> data);

    @Select("select count(*) from tb_recruitment where user_id = #{userId}")
    Integer selectRecritmentCount(Integer userId);
}