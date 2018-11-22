package org.table.neweims.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.table.neweims.entities.User;


public interface UserService {

    //用户注册
    Boolean userRegister(User user);

    //检查用户名是否存在
    Boolean checkUserName(String name);

    //用户登录
    User userLogin(String username);

    //用户信息更改
    User userUpInfo(User user);

    //用户登出
    Boolean userLogout(User user);

    //获取用户角色
    String getUserRole(String username);

    //新用户赋予角色
    void setUserRole(String roleName);

    User getUserInfo(String username);
}
