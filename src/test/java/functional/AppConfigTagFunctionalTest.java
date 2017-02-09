package functional;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by yooyoung-mo on 2017. 2. 9..
 */
public class AppConfigTagFunctionalTest {

    @Test
    public void doTag_active_profile이_없는_경우() throws IOException {
        // given
        String url = "http://localhost:8080/AEP/index.jsp";

        // when
        final WebClient webClient = new WebClient();
        final HtmlPage page = webClient.getPage(url);
        final String pageAsText = page.asText();

        // then
        String expected = "http://daum.net";
        Assert.assertTrue(pageAsText.contains(expected));
    }

    @Test
    public void doTag_active_profile이_dev로_지정된_경우() throws IOException {
        // given
        String url = "http://localhost:8080/AEP/index.jsp";

        // when
        final WebClient webClient = new WebClient();
        final HtmlPage page = webClient.getPage(url);
        final String pageAsText = page.asText();

        // then
        String expected = "http://dev-pg.com";
        Assert.assertTrue(pageAsText.contains(expected));
    }
}
