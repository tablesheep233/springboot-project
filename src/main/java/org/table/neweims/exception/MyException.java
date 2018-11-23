package org.table.neweims.exception;

public class MyException extends RuntimeException {

    private String msg;

    private int code = 500;

    public MyException(String msg,Throwable throwable){
        super(msg,throwable);
        this.msg = msg;
    }

    public MyException(String msg,int code,Throwable throwable){
        super(msg,throwable);
        this.msg = msg;
        this.code = code;
    }

    public MyException(String msg,int code){
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public MyException(String msg){
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
