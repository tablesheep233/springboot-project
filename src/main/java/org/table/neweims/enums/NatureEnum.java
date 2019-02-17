package org.table.neweims.enums;

public enum NatureEnum {

    EMPLOYMENT("就业"),ABROAD("留学"),PUBMED("考研"),PIONEER("创业"),SERVANT("公务员"),ORDER("其他");

    String text;

    NatureEnum(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static NatureEnum getNature(String nature){
        for(NatureEnum n : NatureEnum.values()){
            if (n.getText().equals(nature)){
                return n;
            }
        }
        return null;
    }
}
