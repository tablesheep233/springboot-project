package org.table.neweims;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.table.neweims.util.MySmsSingleSender;
import org.table.neweims.util.VerificationCode;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestQcloudsms {

    @Autowired
    VerificationCode vcode;

    public static void main(String[] args) {

        String[] phoneNumbers = {"13434115696"};

// 短信模板ID，需要在短信应用中申请
        int templateId = 269265; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
//templateId7839对应的内容是"您的验证码是: {1}"
// 签名

        String[] params = {"2333","10"};
        SmsSingleSender ssender = new MySmsSingleSender(1400175003,"cceb68ba7eed11c791ee8ff2beff1a5c");
        try {
            SmsSingleSenderResult result = ssender.sendWithParam("86",phoneNumbers[0],templateId,params,"","","");

        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
        String str[] = {"13822053036"};
        vcode.outSms(str);
    }
}
