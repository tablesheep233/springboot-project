package org.table.neweims.enums;

public enum GenderEnum {

    M("男"),W("女");

    String text;

    GenderEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static GenderEnum getGender(String gender){
        for(GenderEnum g : GenderEnum.values()){
            if (g.getText().equals(gender)){
                return g;
            }
        }
        return null;
    }
}
