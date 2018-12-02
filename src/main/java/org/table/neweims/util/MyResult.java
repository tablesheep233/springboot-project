package org.table.neweims.util;

import java.util.HashMap;

public class MyResult extends HashMap<Object,Object> {

    private Boolean test;

    public MyResult(){
    }

    public MyResult(Object obj){
        this.put("result",obj);
    }

    public MyResult(Boolean test, Object obj){
        this.test = test;
        this.put("result",obj);
    }

    public MyResult(String str, Object obj){
        this.put(str,obj);
    }

    public Boolean getTest() {
        return test;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

    public void setResult(Object obj){
        this.put("result",obj);
    }
}
