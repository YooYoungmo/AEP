package aep;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-09.
 */
public class AppConfigTest {

    @Before
    public void setUp() throws Exception {
        System.setProperty("app.env.profile.active", "");
    }

    @Test
    public void get_개발프로파일() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "dev");

        // when
        Map<String, String> configMap = AppConfig.get("paymentGateway");

        // then
        Assert.assertNotNull(configMap);
        String domain = configMap.get("domain");

        Assert.assertEquals("http://dev-pg.com", domain);
    }

    @Test
    public void get_운영프로파일() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "prod");

        // when
        Map<String, String> configMap = AppConfig.get("paymentGateway");

        // then
        Assert.assertNotNull(configMap);
        String domain = configMap.get("domain");

        Assert.assertEquals("http://pg.com", domain);
    }

    @Test
    public void get_개발프로파일_propertyPath() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "dev");

        // when
        Map<String, String> configMap = AppConfig.get("paymentGateway");

        // then
        Assert.assertNotNull(configMap);
        String domain = configMap.get("propertyPath");

        Assert.assertEquals("classpath:conf/dev-pg.properties", domain);
    }

    @Test
    public void get_프로파일없는경우() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "dev");
        String id ="googleMaps";

        // when
        Map<String, String> configMap = AppConfig.get(id);

        // then
        Assert.assertNotNull(configMap);
        String url = configMap.get("url");

        Assert.assertEquals("http://google.com/maps", url);
    }

    @Test(expected=IllegalArgumentException.class)
    public void get_엘리먼트가_일치_하지_않는_경우() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "dev");

        String id = "naverOpenAPI";

        // when
        AppConfig.get(id);
    }

    @Test(expected=ProfileNotFoundException.class)
    public void get_프로파일_설정이_잘못되어있는경우() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "staging");

        String id = "paymentGateway";

        // when
        AppConfig.get(id);
    }

    @Test
    public void get_프로파일_설정이_되어있지만_프로파일에해당하는_엘리먼트가_없는경우() throws IOException {

    }

    @Test(expected=SystemPropertyNotFoundException.class)
    public void get_systemProperty를_선언하지않은경우() throws IOException {
        // given
        String id = "paymentGateway";

        // when
        AppConfig.get(id);

    }


}
