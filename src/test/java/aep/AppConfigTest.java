package aep;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by 유영모 on 2017-01-09.
 */
public class AppConfigTest {
    @Test
    public void getConfigValue()
    {
        // given
        // when
        AppConfigMap configMap = AppConfig.getConfigValue("paymentGateway");
        String domain = configMap.getConfigValue("domain");
        String propertyPath = configMap.getConfigValue("propertyPath");

        // then
        String expected = "classpath:conf/dev-pg.properties";
        Assert.assertEquals(expected, propertyPath);

    }
}
