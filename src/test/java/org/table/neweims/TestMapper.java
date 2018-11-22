package org.table.neweims;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.table.neweims.entities.User;
import org.table.neweims.mapper.RoleMapper;
import org.table.neweims.mapper.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMapper {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

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
    public void testUp(){
        User user = new User();
        user.setId(1);
        user.setAge(12);
        userMapper.updateUser(user);
    }
}
