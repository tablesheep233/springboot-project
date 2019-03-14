package org.table.neweims.service;

import org.table.neweims.dto.Page;
import org.table.neweims.entities.Student;
import org.table.neweims.util.MyResult;

import java.util.List;

public interface StudentService {

    MyResult getStuInfo(Integer userId);

    MyResult getStuInfo(String username);

    MyResult bindStuInfo(String username,String password);

    void setStu(Student student);

    Integer getStuId(Integer userId);

    Page<Student> getStuList(String name,String major,String clazz,Integer currPage);

    Student getStu(Integer id);

    List<String> getMajorList();

    List<String> getClazzList();
}
