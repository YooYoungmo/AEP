package aep;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-09.
 */
public class AppConfigTest {

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




}
