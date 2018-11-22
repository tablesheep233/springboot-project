package org.table.neweims.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.table.neweims.entities.Student;

import java.io.IOException;

/**
 * 学生信息爬取类
 *
 */
public class HRSysCrawler {

    public Student pyStuInfo(String login,String pw) throws IOException{

        Student student = new Student();

        try(WebClient client = getClient()) {
            HtmlPage loginHRPage = client.getPage("http://class.sise.com.cn:7001/sise/");
            client.waitForBackgroundJavaScript(20000);

            HtmlForm loginForm = loginHRPage.getFormByName("form1");
            HtmlTextInput userInput = loginForm.getInputByName("username");
            HtmlPasswordInput pwInput = loginForm.getInputByName("password");
            userInput.setValueAttribute(login);
            pwInput.setValueAttribute(pw);
            HtmlButtonInput submit = (HtmlButtonInput) loginHRPage.getElementById("Submit");
            HtmlPage mainPage = submit.click();
            client.waitForBackgroundJavaScript(20000);

            HtmlPage stuPage = (HtmlPage) mainPage.getFrameByName("main").getEnclosedPage();
            HtmlPage infoPage = ((HtmlTableDataCell)stuPage.getByXPath("//tr[@title='个人信息查询']/td").get(0)).click();

            HtmlTable table = (HtmlTable) infoPage.getByXPath("//td[@class='tablehead']/table").get(0);

            student.setId(table.getCellAt(0,1).asText());
            student.setName(table.getCellAt(0,3).asText());
            student.setMajor(table.getCellAt(0,7).asText());
            student.setClazz(table.getCellAt(2,1).asText());

        } catch (IOException e) {
            throw e;
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
