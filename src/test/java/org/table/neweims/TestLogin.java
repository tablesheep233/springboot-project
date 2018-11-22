package org.table.neweims;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;

public class TestLogin {

    public static void main(String[] args) throws IOException {
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

        HtmlPage page = client.getPage("http://class.sise.com.cn:7001/sise/");

        client.waitForBackgroundJavaScriptStartingBefore(20000);

        HtmlForm form = page.getFormByName("form1");

        System.out.println(form.asText());

        HtmlTextInput username = form.getInputByName("username");

        HtmlPasswordInput password = form.getInputByName("password");

        username.setValueAttribute("1540706134");

        password.setValueAttribute("858916094abc");

        HtmlButtonInput button = (HtmlButtonInput) page.getElementById("Submit");

        HtmlPage result = button.click();

        client.waitForBackgroundJavaScript(10000);

        HtmlPage iframe = (HtmlPage) result.getFrameByName("main").getEnclosedPage();

        HtmlPage message = ((HtmlTableDataCell) iframe.getByXPath("//tr[@title='个人信息查询']/td").get(0)).click();

        HtmlTable table = (HtmlTable)(message.getByXPath("//td[@class='tablehead']/table").get(0));

        System.out.println(table.getCellAt(0,1).asText());

        System.out.println(table.getCellAt(0,3).asText());

        System.out.println(table.getCellAt(0,5).asText());

        System.out.println(table.getCellAt(0,7).asText());

        System.out.println(table.getCellAt(2,1).asText());

        client.close();
    }
}
