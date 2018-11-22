package org.table.neweims.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.table.neweims.entities.Role;
import org.table.neweims.entities.User;

@Mapper
public interface RoleMapper {


    String selectUserRole(String username);

    @Insert("insert into tb_user_role (user_id,role_id) values (#{userId},#{roleId})")
    void insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    @Select("select id from tb_role where name = #{roleName}")
    Role selectRoleByName(String roleName);
}