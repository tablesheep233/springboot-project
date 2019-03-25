package org.table.neweims.util;

import org.springframework.stereotype.Component;

@Component("cardInfo")
public class CardInfo {

    public String[] getGenderAndBirth(String cno){

        String[] arg = new String[2];

        int no17 = cno.charAt(cno.length()-2);
        if (no17%2 == 0){
            arg[0] = "W";
        } else {
            arg[0] = "M";
        }

        StringBuffer sb = new  StringBuffer(cno.substring(6,14));       //算头不算尾
        sb.insert(4,"-");
        sb.insert(7,"-");
        arg[1] = sb.toString();
        return arg;
    }

    public String resumeReplace(String mystr){
        if(mystr == null || mystr == "" ) {
            return("&nbsp;");
        }
        else {
            mystr = mystr.replace("\n\r","<br>");
            mystr = mystr.replace("\r","<br>");
            mystr = mystr.replace("\t","　　");
            return(mystr);
        }
    }
}
