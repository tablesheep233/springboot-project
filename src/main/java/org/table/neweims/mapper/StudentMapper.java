package org.table.neweims.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.table.neweims.entities.Student;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {


    @Insert("insert into tb_student (id,name,major,clazz,gender,birth,user_id) values (#{id},#{name},#{major},#{clazz},#{gender},#{birth},#{userId})")
    void insertStudent(Student student);

    @Select("select * from tb_student where user_id = #{userId}")
    Student selectStu(Integer userId);

    @Select("select * from tb_student where id = #{id}")
    Student selectStuById(Integer id);

    @Select("select s.* from tb_student s left join tb_user u on s.user_id = u.id where u.username = #{username}")
    Student selectStuByUsername(String username);

    void updateStu(Student student);

    @Select("select id from tb_student where user_id=#{userId}")
    Integer selectStuId(Integer userId);

    List<Map<String,Object>> selectStuByPage(@Param("name") String name,@Param("major") String major, @Param("clazz") String clazz, @Param("data") Map<String,Object> data);

    Integer selectStuCount(@Param("name") String name,@Param("major") String major, @Param("clazz") String clazz);

    @Select("select distinct major from tb_student")
    List<String> selectMajorList();

    @Select("select distinct clazz from tb_student")
    List<String> selectClazzList();


}