package org.table.neweims.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.table.neweims.entities.User;
import org.table.neweims.service.UserService;

import java.util.HashSet;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

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
        roles.add(userService.getUserRole(username));
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);


        return info;
    }


}
