package org.table.neweims.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.table.neweims.config.ymlpojo.PageConf;
import org.table.neweims.dto.Page;
import org.table.neweims.entities.Student;
import org.table.neweims.entities.User;
import org.table.neweims.enums.GenderEnum;
import org.table.neweims.exception.MyException;
import org.table.neweims.mapper.StudentMapper;
import org.table.neweims.service.StudentService;
import org.table.neweims.util.HRSysCrawler;
import org.table.neweims.util.MyResult;
import org.table.neweims.util.SysContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "studentService")
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private PageConf pageConf;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private HRSysCrawler hrSysCrawler;

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
    public MyResult getStuInfo(String username) {
        Student student = null;
        MyResult result = null;
        student = studentMapper.selectStuByUsername(username);
        if (student != null){
            return result = new MyResult(true,student);
        }else{
            return result = new MyResult(false,null);
        }
    }

    @Override
    public MyResult bindStuInfo(String username, String password) throws MyException {


        Student student = hrSysCrawler.pyStuInfo(username,password);
        student.setUserId(SysContext.getCurrentUser());

        studentMapper.insertStudent(student);
        MyResult result = new MyResult(student);
        return result;
    }

    @Override
    public void setStu(Student student) {
        studentMapper.updateStu(student);
    }

    @Override
    public Integer getStuId(Integer userId) {
        return studentMapper.selectStuId(userId);
    }

    @Override
    public Page<Student> getStuList(String name,String major,String clazz,Integer currPage) {
        Map<String, Object> data = new HashMap<>();
        Integer pageSize = pageConf.getApplyLimit();

        data.put("currPage",currPage);
        data.put("pageSize",pageSize);


        if (name!=null&&!name.equals("")){
            name = "%"+name+"%";
        }

        List<Map<String,Object>> list = studentMapper.selectStuByPage(name,major,clazz,data);

        Integer totalCount = studentMapper.selectStuCount(name,major,clazz);

        Page<Student> page = new Page<>(currPage,pageSize,totalCount);

        page.setMyResult(list);

        return page;
    }

    @Override
    public Student getStu(Integer id) {
        return studentMapper.selectStuById(id);
    }

    @Override
    public List<String> getMajorList() {
        return studentMapper.selectMajorList();
    }

    @Override
    public List<String> getClazzList() {
        return studentMapper.selectClazzList();
    }
}
