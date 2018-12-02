package org.table.neweims.interceptor;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 表单重复提交拦截器
 * 原理：在进入表单前在服务器保存一个token，页面提交时比较页面的token与服务器的是否一致
 *       一致则删除服务器token，允许提交
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {


    private static Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null){
                String tokenName = annotation.tokenName();
                boolean saveToken = annotation.saveToken();
                if (saveToken){
                    request.getSession(true).setAttribute(tokenName, UUID.randomUUID().toString());
                }
                boolean removeToken = annotation.removeToken();
                if (removeToken){
                    if (isRepeatSubmit(request,tokenName)){
                        return false;
                    }
                    request.getSession(false).removeAttribute(tokenName);
                }
            }
        }

        return true;
    }


    /**
     * 比较token
     * @param request
     * @param tokenName
     * @return
     */
    private boolean isRepeatSubmit(HttpServletRequest request,String tokenName){
        String serverToken = (String) request.getSession().getAttribute(tokenName);
        if (serverToken == null){
            return true;
        }
        String clinetToken = (String) request.getParameter(tokenName);
        if (clinetToken == null){
            return true;
        }
        if (!serverToken.equals(clinetToken)){
            return true;
        }
        return false;
    }
}
