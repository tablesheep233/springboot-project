package org.table.neweims.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.table.neweims.entities.Student;
import org.table.neweims.entities.User;
import org.table.neweims.exception.MyException;
import org.table.neweims.mapper.StudentMapper;
import org.table.neweims.service.StudentService;
import org.table.neweims.util.HRSysCrawler;
import org.table.neweims.util.MyResult;

@Service(value = "studentService")
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public MyResult getStuInfo(Integer userId) {
        Student student = null;
        MyResult result = null;
        student = studentMapper.selectStu(userId);
        if (student != null){
            return result = new MyResult(true,student);
        }else{
            return result = new MyResult(false,null);
        }
    }

    @Override
    public MyResult bindStuInfo(String username, String password) throws MyException {

        HRSysCrawler hrSysCrawler = new HRSysCrawler();

        Student student = hrSysCrawler.pyStuInfo(username,password);
        Session session = SecurityUtils.getSubject().getSession();
        student.setUserId((Integer) session.getAttribute("loginUser"));

        studentMapper.insertStudent(student);
        MyResult result = new MyResult(student);
        return result;
    }

    @Override
    public void setStu(Student student) {
        studentMapper.updateStu(student);
    }
}
