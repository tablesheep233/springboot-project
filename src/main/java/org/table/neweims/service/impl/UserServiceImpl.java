package org.table.neweims.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.table.neweims.entities.User;
import org.table.neweims.mapper.ResourceMapper;
import org.table.neweims.mapper.RoleMapper;
import org.table.neweims.mapper.UserMapper;
import org.table.neweims.service.UserService;
import org.table.neweims.util.Encryption;

import java.util.List;
import java.util.UUID;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    //判断用户名是否存在
    @Override
    public Boolean checkUserName(String name) {
        if(userMapper.selectUserName(name)!=null)
        {
            //存在返回false
            return false;
        }
        return true;
    }

    /**
     * 用户注册，成功返回true
     * @param user
     * @return
     */
    @Override
    public Boolean userRegister(User user) {

        UUID uuid = UUID.randomUUID();
        user.setSalt(uuid.toString().replace("-",""));
        user.setPassword(Encryption.getEncryption(user.getPassword(),user.getSalt()));
        try{
            userMapper.insertUser(user);
            return true;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public User userLogin(String username) {
        User user = userMapper.selectUserByName(username);
        return user;
    }

    @Override
    public Boolean userLogout(User user) {
        return null;
    }

    /**
     * 获取用户角色
     * @param username
     * @return
     */
    @Override
    public String getUserRole(String username) {
        return roleMapper.selectUserRole(username);
    }

    /**
     * 利用shiro的session获取httpsession存放的登录用户
     * 设置用户的角色
     * @param roleName
     */
    @Override
    public void setUserRole(String username,String roleName) {
        Integer userId = userMapper.selectUserByName(username).getId();
        roleMapper.insertUserRole(userId,roleMapper.selectRoleByName(roleName).getId());
    }

    /**
     * 返回登录用户信息，去掉部分属性
     * @param username
     * @return
     */
    @Override
    public User getUserInfo(String username) {
        User user = userMapper.selectUserByName(username);
        user.setPassword("");
        user.setSalt("");
        return user;
    }

    @Override
    public User getUserInfo(Integer id) {
        User user = userMapper.selectUserById(id);
        user.setPassword("");
        user.setSalt("");
        return user;
    }

    @Override
    public List<String> getUserPerms(String name) {
        return resourceMapper.selectPermsByUsername(name);
    }
}
