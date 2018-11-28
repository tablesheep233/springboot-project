package org.table.neweims;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.table.neweims.util.MyResult;

//@SpringBootTest
//@RunWith(SpringRunner.class)
public class TestClass {

    //@Test
    public void testMyResult(){
        MyResult result = new MyResult(233);
        System.out.println(result.get("result"));
    }

    public static void main(String[] args) {
        System.out.println(9%2);
    }
}
