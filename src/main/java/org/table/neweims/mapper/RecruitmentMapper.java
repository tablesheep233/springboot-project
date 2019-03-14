package org.table.neweims.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.table.neweims.dto.RecruitmentDto;
import org.table.neweims.entities.Recruitment;
import org.table.neweims.enums.StatusEnum;

import java.util.List;
import java.util.Map;

@Mapper
public interface RecruitmentMapper {

    List<Map<String,Object>> getAllRecruitmentByPage(@Param("userId") Integer userId,@Param("name") String name, @Param("data") Map<String,Object> data, @Param("statu")StatusEnum statusEnum);

    Integer selectRecritmentCount(@Param("userId") Integer userId, @Param("name") String name, @Param("statu")StatusEnum statusEnum);

    Map<String,Object> queryRecruitmentById(Integer id);

    void insertRecruitment(Recruitment recruitment);

    void updateRecruitment(Recruitment recruitment);

    @Delete("delete from tb_recruitment where id = #{id}")
    void deleteRecruitment(Integer id);

    List<RecruitmentDto> getAllSRByPage(@Param("userId") Integer userId, @Param("name") String name, @Param("data") Map<String,Object> data);

    List<Map<String,Object>> getSSData(@Param("date")String date,@Param("userId")Integer userId);
}