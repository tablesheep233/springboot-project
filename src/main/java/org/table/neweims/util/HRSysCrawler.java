package org.table.neweims.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.table.neweims.entities.Student;
import org.table.neweims.enums.GenderEnum;
import org.table.neweims.exception.MyException;

import java.io.IOException;

/**
 * 学生信息爬取类
 *
 */
public class HRSysCrawler {

    public Student pyStuInfo(String login,String pw){

        Student student = null;

        HtmlTable table = null;

        try(WebClient client = getClient()) {
            HtmlPage loginHRPage = client.getPage("http://class.sise.com.cn:7001/sise/");
            client.waitForBackgroundJavaScript(20000);

            HtmlForm loginForm = loginHRPage.getFormByName("form1");
            HtmlTextInput userInput = loginForm.getInputByName("username");
            HtmlPasswordInput pwInput = loginForm.getInputByName("password");
            userInput.setValueAttribute(login);
            pwInput.setValueAttribute(pw);
            HtmlButtonInput submit = (HtmlButtonInput) loginHRPage.getElementById("Submit");
            HtmlPage mainPage = null;
            HtmlPage stuPage = null;

            try{
                mainPage = submit.click();
                stuPage = (HtmlPage) mainPage.getFrameByName("main").getEnclosedPage();
                client.waitForBackgroundJavaScript(20000);
            }catch (Exception e){
                throw new RuntimeException("学号或密码错误",e);
            }

            HtmlPage infoPage = null;

            try{
                infoPage = ((HtmlTableDataCell)stuPage.getByXPath("//tr[@title='个人信息查询']/td").get(0)).click();
                table = (HtmlTable) infoPage.getByXPath("//td[@class='tablehead']/table").get(0);
            }catch (Exception e){
                throw new RuntimeException("学生系统异常,或处于选课时期",e);
            }
        } catch (RuntimeException e){
            throw new MyException(e.getMessage(),e);
        } catch (Exception e) {
            throw new MyException("学生系统服务器异常",e);
        }

        if(table != null){
            student = new Student();
            student.setId(table.getCellAt(0,1).asText());
            student.setName(table.getCellAt(0,3).asText());
            student.setMajor(table.getCellAt(0,7).asText());
            student.setClazz(table.getCellAt(2,1).asText());
            String arg[] = CardInfo.getGenderAndBirth(table.getCellAt(1,1).asText());
            student.setGender(arg[0]);
            student.setBirth(arg[1]);
        }

        return student;
    }


    private WebClient getClient(){
        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setTimeout(5000);
        client.setJavaScriptTimeout(10000*3);
        client.getOptions().setRedirectEnabled(true);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setUseInsecureSSL(false);
        client.setAjaxController(new NicelyResynchronizingAjaxController());
        client.getCookieManager().setCookiesEnabled(true);
        return client;
    }
}
