package org.table.neweims.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.table.neweims.entities.User;
import org.table.neweims.service.EnterpriseService;
import org.table.neweims.service.StudentService;
import org.table.neweims.service.UserService;
import org.table.neweims.util.MyResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private StudentService studentService;

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.userLogin(token.getUsername());

        Object principal = user.getUsername();
        Object hashedCredentials = user.getPassword();
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
        String realmName = getName();

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, hashedCredentials, credentialsSalt, realmName);
        return info;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

       String username = (String) principalCollection.getPrimaryPrincipal();

        Set<String> roles = new HashSet<>();
        String role = userService.getUserRole(username);
        roles.add(role);

        Set<String> perms = new HashSet<>();
        List<String> plist = userService.getUserPerms(username);
        for (String p : plist) {
            perms.add(p);
        }


        if (role!=null&&role.equals("enterprise")){
            List<String> permsList = enterpriseService.isLegalEnterprise(username);
            for (String p : permsList) {
                perms.add(p);
            }
        } else if(role!=null&&role.equals("student")){
            MyResult result = studentService.getStuInfo(username);
            if (!result.getTest()){
                perms.add("stu:need");
            }else{
                perms.add("stu:real");
            }
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(perms);
        return info;
    }
}
