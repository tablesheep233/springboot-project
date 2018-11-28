package org.table.neweims;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.table.neweims.entities.Student;
import org.table.neweims.entities.User;
import org.table.neweims.mapper.ResumeMapper;
import org.table.neweims.mapper.RoleMapper;
import org.table.neweims.mapper.StudentMapper;
import org.table.neweims.mapper.UserMapper;

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
        User user = new User();
        user.setUsername("table");
        //System.out.println(roleMapper.selectUserRole(user));
        //System.out.println(userMapper.selectUserByName("table"));
        //System.out.println(userMapper.selectUserName("0"));
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
