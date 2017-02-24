package aep;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by 유영모 on 2017-01-09.
 */
public class AppConfigTest {
    @Test
    public void getConfigValue()
    {
        // given
        System.setProperty("app.env.profile.active", "dev");

        // when
        Map<String, String> configValue = AppConfig.getConfigValue("paymentGateway");
        String propertyPath = configValue.get("propertyPath");

        // then
        String expected = "classpath:conf/dev-pg.properties";
        Assert.assertEquals(expected, propertyPath);

    }
}
