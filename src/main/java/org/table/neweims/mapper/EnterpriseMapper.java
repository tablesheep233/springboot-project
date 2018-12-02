package org.table.neweims.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.table.neweims.entities.Enterprise;

@Mapper
public interface EnterpriseMapper {

    void insertEnterprise(Enterprise enterprise);

    void updataEnterprise(Enterprise enterprise);

    Integer selectEnterpriseStatus(String username);

    @Select("select * from tb_enterprise where user_id = #{userId}")
    Enterprise selectEnterpriseByUser(Integer userId);
}