package aep;

import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-09.
 */
public class AppConfigTest {
    @Test
    public void getConfigValue_dev_activeProfile() throws IOException {
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

    @Test
    public void getConfigValue_stg_activeProfile() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "stag");

        // when
        Map<String, String> configValue = AppConfig.getConfigValue("paymentGateway");
        String actualPropertyPath = configValue.get("propertyPath");
        String actualDomain = configValue.get("domain");

        // then
        String expectedPropertyPath = "classpath:conf/stg-pg.properties";
        Assert.assertEquals(expectedPropertyPath, actualPropertyPath);
        String expectedDomain = "http://stg-pg.com";
        Assert.assertEquals(expectedDomain, actualDomain);
    }

    @Test
    public void getConfigValue_prod_activeProfile() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "prod");

        // when
        Map<String, String> configValue = AppConfig.getConfigValue("paymentGateway");
        String actualPropertyPath = configValue.get("propertyPath");
        String actualDomain = configValue.get("domain");

        // then
        String expectedPropertyPath = "classpath:conf/prod-pg.properties";
        Assert.assertEquals(expectedPropertyPath, actualPropertyPath);
        String expectedDomain = "http://prod-pg.com";
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

    @Test(expected = NotFoundConfigValueException.class)
    public void getConfigValue_invalid_key() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "prod");

        // when
        try {
            Map<String, String> configValue = AppConfig.getConfigValue("invalidKey");
            String actualPropertyPath = configValue.get("propertyPath");

            // then
            String expectedPropertyPath = "classpath:conf/prod-pg.properties";
            Assert.assertEquals(expectedPropertyPath, actualPropertyPath);
        } catch (RuntimeException e) {
            System.out.print("runtimeException catch!!");
            throw e;
        }
    }

    @Test
    public void getConfigValue_defaultConfigValue() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "dev");

        // when
        Map<String, String> map = AppConfig.getConfigValue("googleMaps");
        String actual = map.get("url");
        String expected = "http://google.com/maps";

        // then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getActiveProfile() {
        // given
        String givenActiveProfile = "dev";
        System.setProperty("app.env.profile.active", givenActiveProfile);

        // when
        String activeProfile = AppConfig.getActiveProfile();

        // then
        Assert.assertEquals(givenActiveProfile, activeProfile);
    }

    @Test
    public void validActiveProfile() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "dev");

        // when
        boolean validAtiveProfile = AppConfig.validActiveProfile();
        boolean expected = true;

        // then
        Assert.assertEquals(expected, validAtiveProfile);
    }

    @Test
    public void invalidActiveProfile() throws IOException {
        // given
        System.setProperty("app.env.profile.active", "invalid");

        // when
        boolean validAtiveProfile = AppConfig.validActiveProfile();
        boolean expected = false;

        // then
        Assert.assertEquals(expected, validAtiveProfile);
    }

    @Test(expected = WrongFormatAppConfigTextException.class)
    public void parseJSONObject_wrongFormatText() {
        // given
        String appConfigText = "wrongText";

        // when
        JSONObject jsonObject = AppConfig.parseJSONObject(appConfigText);

        // then

    }

    @Test
    public void parseJSONObject_sameReference() throws IOException {
        // given
        String appConfigText = AppConfigFileLoader.getText();

        // when
        JSONObject jsonObjectFirst = AppConfig.parseJSONObject(appConfigText);

        JSONObject jsonObjectSecond = AppConfig.parseJSONObject(appConfigText);

        // then
        Assert.assertSame(jsonObjectFirst, jsonObjectSecond);
    }
}
