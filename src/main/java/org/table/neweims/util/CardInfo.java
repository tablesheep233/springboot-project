package org.table.neweims.util;

public class CardInfo {

    public static String[] getGenderAndBirth(String cno){

        String[] arg = new String[2];
        int no17 = cno.charAt(cno.length()-2);
        if (no17%2 == 0){
            arg[0] = "w";
        } else {
            arg[0] = "m";
        }
        StringBuffer sb = new  StringBuffer(cno.substring(6,14));       //算头不算尾
        sb.insert(4,"-");
        sb.insert(7,"-");
        arg[1] = sb.toString();
        return arg;
    }


}
