package org.table.neweims.interceptor;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class,Integer.class})})
public class MybatisPageInterceptor implements Interceptor{

    private int pageSize;

    private int currPage;

    private String dbType;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        while (metaObject.hasGetter("h")) {
            Object obj = metaObject.getValue("h");
            metaObject = SystemMetaObject.forObject(obj);
        }

        while (metaObject.hasGetter("target")) {
            Object obj = metaObject.getValue("target");
            metaObject = SystemMetaObject.forObject(obj);
        }

        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        String mapId = mappedStatement.getId();

        //statementHandler.getBoundSql().getParameterObject();

        //拦截以.ByPage结尾的请求，分页功能的统一实现
        if (mapId.matches(".+ByPage$")) {
            //获取进行数据库操作时管理参数的handler
            ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
            //获取请求时的参数
            Map<String, Object> paraObject = (Map<String, Object>) parameterHandler.getParameterObject();
            //也可以这样获取
            //paraObject = (Map<String, Object>) statementHandler.getBoundSql().getParameterObject();

            //参数名称和在service中设置到map中的名称一致
            Map<String,Object> data = (Map<String, Object>) paraObject.get("data");
            currPage = (int) data.get("currPage");
            pageSize = (int) data.get("pageSize");

            String sql = (String) metaObject.getValue("delegate.boundSql.sql");
            //也可以通过statementHandler直接获取
            //sql = statementHandler.getBoundSql().getSql();

            //构建分页功能的sql语句
            String limitSql;
            sql = sql.trim();
            limitSql = sql + " limit " + (currPage - 1) * pageSize + "," + pageSize;

            //将构建完成的分页sql语句赋值个体'delegate.boundSql.sql'，偷天换日
            metaObject.setValue("delegate.boundSql.sql", limitSql);
        }
//调用原对象的方法，进入责任链的下一级
        return invocation.proceed();

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {
        String limit = properties.getProperty("limit", "10");
        this.pageSize = Integer.valueOf(limit);
        this.dbType = properties.getProperty("dbType", "mysql");
    }
}
