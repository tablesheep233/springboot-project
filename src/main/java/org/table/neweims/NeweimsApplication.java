package org.table.neweims;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.table.neweims.mapper")
@SpringBootApplication
public class NeweimsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeweimsApplication.class, args);
    }
}
