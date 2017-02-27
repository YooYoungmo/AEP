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
    public void getConfigValue() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "dev");

        // when
        Map<String, String> configValue = AppConfig.getConfigValue("paymentGateway");
        String actualPropertyPath = configValue.get("propertyPath");
        String actualDomain = configValue.get("domain");

        // then
        String expectedPropertyPath = "classpath:conf/dev-pg.properties";
        Assert.assertEquals(expectedPropertyPath, actualPropertyPath);
        String expectedDomain = "http://dev-pg.com";
        Assert.assertEquals(expectedDomain, actualDomain);
    }

    @Test(expected = InvalidActiveProfileException.class)
    public void getConfigValue_invalid_activeProfile() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "invalid");
        // when
        Map<String, String> configValue = AppConfig.getConfigValue("paymentGateway");
        String actualPropertyPath = configValue.get("propertyPath");

        // then
        String expectedPropertyPath = "classpath:conf/dev-pg.properties";
        Assert.assertEquals(expectedPropertyPath, actualPropertyPath);
    }
}
