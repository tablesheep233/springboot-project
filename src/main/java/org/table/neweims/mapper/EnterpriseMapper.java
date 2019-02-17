package org.table.neweims.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.table.neweims.dto.EnterpriseDto;
import org.table.neweims.entities.Enterprise;

import java.util.List;
import java.util.Map;

@Mapper
public interface EnterpriseMapper {

    void insertEnterprise(Enterprise enterprise);

    void updataEnterprise(Enterprise enterprise);

    String selectEnterpriseStatus(String username);

    @Select("select * from tb_enterprise where user_id = #{userId}")
    Enterprise selectEnterpriseByUser(Integer userId);

    @Select("select * from tb_enterprise where id = #{id}")
    Enterprise selectEnterpriseById(Integer id);

    List<EnterpriseDto> selectEnterpriseByPage(@Param("name") String name, @Param("status") String status, @Param("data")Map<String,Object> data);

    Integer selectEnterpriseCount(@Param("name") String name, @Param("status") String status);
}