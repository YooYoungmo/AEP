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
    public void get_프로파일없는경우() throws IOException {
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
    public void get_엘리먼트가_일치_하지_않는_경우() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "dev");

        String id = "naverOpenAPI";

        // when
        AppConfig.getConfigValue(id);
    }

    @Test
    public void get_프로파일_설정이_되어있지만_프로파일에해당하는_엘리먼트가_없는경우() throws IOException {

    }

    @Test(expected=SystemPropertyNotFoundException.class)
    public void get_systemProperty를_선언하지않은경우() throws IOException {
        // given
        String id = "paymentGateway";

        // when
        AppConfig.getConfigValue(id);

    }

    @Test
    public void get_systemProperty로_전달받은_값이올바를때() throws IOException {
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
    public void get_systemProperty로_전달받은_값이_aepConfig에_없는값이전달되었을때() throws IOException {
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
    public void get_activeProfile이_지정되어있으며_키_값이_중복선언되어있는경우() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "prod");
        String id = "googleMaps";

        // when
        Map<String, String> actual = AppConfig.getConfigValue(id);

        // then
        String expected = "http://googletest.com/maps";

        Assert.assertEquals(expected, actual.get("url"));

    }

    @Test
    public void get_activeProfile이_지정되어있으나_키_값이_configProfile에_없는경우() throws IOException {
        //TODO 케이스 생각해 보기
//        // given
//        System.setProperty("app.env.profile.active", "prod");
//        String id = "openApi";
//
//        // when
//        Map<String, String> actual = AppConfig.getConfigValue(id);
//
//        // then
//        String expected = "http://daum.net";
//
//        Assert.assertEquals(expected, actual.getConfigValue("url"));

    }



}
