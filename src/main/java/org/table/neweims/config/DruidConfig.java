package org.table.neweims.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * druid数据源配置类
 */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource duridDataSource(){
        return new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean statViewServlet(){

        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,Object> map = new HashMap<>();
        map.put("loginUsername","admin");
        map.put("loginPasswor","admin");
        map.put("allow","");

        bean.setInitParameters(map);

        return bean;
    }

    @Bean
    public FilterRegistrationBean webStatFilter(){

        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());
        Map<String,Object> map = new HashMap<>();
        map.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(map);
        bean.setUrlPatterns(Arrays.asList("/"));
        return bean;
    }
}
