package org.table.neweims;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import org.table.neweims.config.ymlpojo.PageConf;
import org.table.neweims.util.MyResult;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestClass {

    @Autowired
    private PageConf pageConf;

    //@Test
    public void testMyResult(){
        MyResult result = new MyResult(233);
        System.out.println(result.get("result"));
    }

    @Test
    public void testYml(){
        System.out.println(pageConf.getRecruitmentLimit());
    }

    public static void main(String[] args) {
        System.out.println(9%2);
    }
}
