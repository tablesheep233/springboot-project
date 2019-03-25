package org.table.neweims.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

public class SysContext {

    public synchronized static Integer getCurrentUser(){
        Session session = SecurityUtils.getSubject().getSession();
        Integer userId = (Integer) session.getAttribute("loginUser");
        return userId;
    }
}
