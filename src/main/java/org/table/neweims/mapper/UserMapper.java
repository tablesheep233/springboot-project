package org.table.neweims.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.table.neweims.entities.User;

@Mapper
public interface UserMapper {

    @Insert("insert into tb_user (username,password,salt) values (#{username},#{password},#{salt})")
    void insertUser(User user);

    void updateUser(User user);


    @Select("select username from tb_user where username = #{name}")
    String selectUserName(String name);

    @Select("select * from tb_user where username = #{username}")
    User selectUserByName(String username);

}