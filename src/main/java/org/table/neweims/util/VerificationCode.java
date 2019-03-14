package org.table.neweims.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.table.neweims.config.ymlpojo.Code;

import java.io.IOException;
import java.util.Random;

@Component
public class VerificationCode {

    @Autowired
    private Code code;

    private final String texts = "0123456789";

    private Random r = new Random();

    public String outSms(String[] phoneNumbers){
        String text = getText();
        String[] params = {text,"10"};
        SmsSingleSender ssender = new MySmsSingleSender(code.getId(),code.getKey());
        try {
            SmsSingleSenderResult result = ssender.sendWithParam("86",phoneNumbers[0],code.getTemplateId(),params,"","","");
        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public String outRSms(String[] phoneNumbers){
        String text = getText();
        String[] params = {text};
        SmsSingleSender ssender = new MySmsSingleSender(code.getId(),code.getKey());
        try {
            SmsSingleSenderResult result = ssender.sendWithParam("86",phoneNumbers[0],code.getResetTemplateId(),params,"","","");
        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    private String getText(){
        StringBuffer sbf = new StringBuffer();
        for(int i = 0 ; i < 4 ; i++){
            sbf.append(randomText());
        }
        return sbf.toString();
    }

    private char randomText(){
        int i = r.nextInt(texts.length());
        return texts.charAt(i);
    }
}
