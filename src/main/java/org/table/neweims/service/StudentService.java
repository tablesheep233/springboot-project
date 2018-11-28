package org.table.neweims.service;

import org.table.neweims.entities.Student;
import org.table.neweims.util.MyResult;

public interface StudentService {

    MyResult getStuInfo(Integer userId);

    MyResult bindStuInfo(String username,String password);

    void setStu(Student student);
}
