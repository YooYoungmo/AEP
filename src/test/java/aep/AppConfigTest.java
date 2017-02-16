package aep;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
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
    public void getConfigValue_activeProfile이_dev인경우() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "dev");

        // when
        Map<String, String> configMap = AppConfig.getConfigValue("paymentGateway");

        // then
        Assert.assertNotNull(configMap);
        String domain = configMap.get("domain");
        Assert.assertEquals("http://dev-pg.com", domain);

        String propertyPath = configMap.get("propertyPath");
        Assert.assertEquals("classpath:conf/dev-pg.properties", propertyPath);

    }

    @Test
    public void getConfigValue_activeProfile이_prod인경우() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "prod");

        // when
        Map<String, String> configMap = AppConfig.getConfigValue("paymentGateway");

        // then
        Assert.assertNotNull(configMap);
        String domain = configMap.get("domain");

        Assert.assertEquals("http://pg.com", domain);
    }

    @Test
    public void getConfigValue_defaultConfigValue() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "dev");
        String id ="googleMaps";

        // when
        Map<String, String> configMap = AppConfig.getConfigValue(id);

        // then
        Assert.assertNotNull(configMap);
        String url = configMap.get("url");

        Assert.assertEquals("http://google.com/maps", url);
    }

    @Test(expected=IllegalArgumentException.class)
    public void getConfigValue_invalidKey() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "dev");

        String id = "naverOpenAPI";

        // when
        AppConfig.getConfigValue(id);
    }

    @Test(expected=SystemPropertyNotFoundException.class)
    public void getConfigValue_activeProfile_systemProperty로_전달되지않은경우() throws IOException {
        // given
        String id = "paymentGateway";

        // when
        AppConfig.getConfigValue(id);
    }

    @Test
    public void getConfigValue_activeProfile이_유효한경우() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "stag");
        String id = "paymentGateway";

        // when
        Map<String, String> actual = AppConfig.getConfigValue(id);

        // then
        Assert.assertNotNull(actual);

        Map<String, String> expected = new HashMap<String, String>();
        expected.put("domain", "http://stg-pg.com");
        expected.put("propertyPath", "classpath:conf/stg-pg.properties");

        Assert.assertEquals(expected.get("domain"), actual.get("domain"));
        Assert.assertEquals(expected.get("propertyPath"), actual.get("propertyPath"));
    }

    @Test(expected = SystemPropertyInvalidValueException.class)
    public void getConfigValue_activeProfile이_유효하지_않은_경우() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "invalid");
        String id = "paymentGateway";

        // when
        Map<String, String> actual = AppConfig.getConfigValue(id);

        // then
        Assert.assertNotNull(actual);

        Map<String, String> expected = new HashMap<String, String>();
        expected.put("domain", "http://stg-pg.com");
        expected.put("propertyPath", "classpath:conf/stg-pg.properties");

        Assert.assertEquals(expected.get("domain"), actual.get("domain"));
        Assert.assertEquals(expected.get("propertyPath"), actual.get("propertyPath"));
    }

    @Test
    public void getConfigValue_Key가_defaultConfigValue와_configProfile이_충돌나는_경우() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "prod");
        String id = "googleMaps";

        // when
        Map<String, String> actual = AppConfig.getConfigValue(id);

        // then
        String expected = "http://googletest.com/maps";

        Assert.assertEquals(expected, actual.get("url"));
    }

}
