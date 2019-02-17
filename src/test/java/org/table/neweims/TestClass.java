package org.table.neweims;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.table.neweims.config.ymlpojo.PageConf;
import org.table.neweims.enums.GenderEnum;
import org.table.neweims.enums.NatureEnum;
import org.table.neweims.util.MyResult;

//@SpringBootTest
//@RunWith(SpringRunner.class)
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
        NatureEnum[] natureEnums = NatureEnum.values();
        for (NatureEnum n:natureEnums) {
            System.out.println(n.getText());
        }
    }
}
