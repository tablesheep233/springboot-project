package org.table.neweims.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.table.neweims.entities.Student;

@Mapper
public interface StudentMapper {


    @Insert("insert into tb_student (id,name,major,clazz,gender,birth,user_id) values (#{id},#{name},#{major},#{clazz},#{gender},#{birth},#{userId})")
    void insertStudent(Student student);

    @Select("select * from tb_student where user_id = #{userId}")
    Student selectStu(Integer userId);

    @Select("select s.* from tb_student s left join tb_user u on s.user_id = u.id where u.username = #{username}")
    Student selectStuByUsername(String username);

    void updateStu(Student student);
}