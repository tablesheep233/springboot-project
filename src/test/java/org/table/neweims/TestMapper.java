package org.table.neweims;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.table.neweims.dto.Page;
import org.table.neweims.entities.Recruitment;
import org.table.neweims.entities.Student;
import org.table.neweims.entities.User;
import org.table.neweims.mapper.*;
import org.table.neweims.service.RecruitmentService;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMapper {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    ResumeMapper resumeMapper;

    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    RecruitmentService recruitmentService;

    @Autowired
    RecruitmentMapper recruitmentMapper;

    @Test
    public void testUser() {

        User user = new User();
        user.setUsername("2333");
        user.setPassword("2333");
        user.setSalt("222");
        userMapper.insertUser(user);
    }

    @Test
    public void testQuery(){
//        List<String> list = resourceMapper.selectPermsByUsername("table");
//        for (String s:list) {
//            System.out.println(s);
//        }

        Page<Recruitment> list = recruitmentService.queryStudentsByPage(1);
        for (Recruitment s:list.getResult()) {
            System.out.println(s.getJob());
        }

//        System.out.println(recruitmentMapper.selectRecritmentCount(6));
    }


    @Test
    public void testStu() {
        Student student = null;
        student =  studentMapper.selectStu(133);
        if (student != null){
            System.out.println(2);
            System.out.println(student.getClazz());
        }
    }

    @Test
    public void testResume(){
        List<Map<String,Object>> list = resumeMapper.selectListResume(1);
        for (Map<String,Object> m : list) {
            System.out.println(m.get("rname"));
        }
    }
}
