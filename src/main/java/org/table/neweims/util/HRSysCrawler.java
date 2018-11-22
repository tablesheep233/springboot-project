package org.table.neweims.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.table.neweims.entities.Student;

import java.io.IOException;

public class HRSysCrawler {

    public Student pyStuInfo(String login,String pw){
        Student student = new Student();
        WebClient client = getClient();

        try {
            HtmlPage loginHR = client.getPage("http://class.sise.com.cn:7001/sise/");
        } catch (IOException e) {
            e.printStackTrace();
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
