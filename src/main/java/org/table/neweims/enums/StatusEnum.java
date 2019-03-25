package org.table.neweims.enums;

public enum StatusEnum {

    NEED(1,"需要审核"),WAIT(2,"待审"),REAL(3,"通过"),UNREAL(4,"不通过"),COLLECT(5,"收藏"),READ(6,"已读"),NOTREAD(7,"未读");

    private Integer code;

    private String message;

    StatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static StatusEnum getStatus(String status){
        for(StatusEnum s : StatusEnum.values()){
            if (s.getMessage().equals(status)){
                return s;
            }
        }
        return null;
    }
}
