package org.table.neweims.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.table.neweims.realm.MyShiroRealm;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 注入自定义的Realm
     * 必须注入
     * @return
     */
    @Bean(name = "myShiroRealm")
    public MyShiroRealm myShiroRealm(
            @Qualifier ("hashedCredentialsMatcher") HashedCredentialsMatcher matcher){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }


    /**
     * 注入securityManager
     * @return
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager(
            @Qualifier ("myShiroRealm") MyShiroRealm realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(2333);
        return hashedCredentialsMatcher;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(
            @Qualifier ("securityManager") SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");

        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/register","anon");
        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/user/**","anon");
        filterChainDefinitionMap.put("/logout","logout");
        filterChainDefinitionMap.put("/mystatic/**","anon");
        filterChainDefinitionMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
}
