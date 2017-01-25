package aep;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by yooyoung-mo on 2017. 1. 10..
 */
public class AppConfigFileLoaderTest {

    @Test
    public void getText() throws IOException {
        // given
        AppConfigFileLoader appConfigFileLoader = new AppConfigFileLoader("conf/app-config.json");

        // when
        String actual = appConfigFileLoader.getText();

        // then
        String expected = "{  \"profile\": {    \"dev\": {      \"paymentGateway\": {        \"domain\": \"http://dev-pg.com\",        \"propertyPath\": \"classpath:conf/dev-pg.properties\"      }    },    \"stag\": {      \"paymentGateway\": {        \"domain\": \"http://stg-pg.com\",        \"propertyPath\": \"classpath:conf/stg-pg.properties\"      }    },    \"prod\": {      \"paymentGateway\": {        \"domain\": \"http://pg.com\",        \"propertyPath\": \"classpath:conf/dev-pg.properties\"      },      \"googleMaps\" : {        \"url\" : \"http://googletest.com/maps\"      }    }  },  \"googleMaps\" : {    \"url\" : \"http://google.com/maps\"  },  \"openApi\" : {    \"url\" :\"http://daum.net\"  }}";
        Assert.assertEquals(expected, actual);
    }

    @Test(expected=FileNotFoundException.class)
    public void getText_파일없는케이스() throws IOException {
        // given
        AppConfigFileLoader appConfigFileLoader = new AppConfigFileLoader("nothing.json");

        // when
        String actual = appConfigFileLoader.getText();

        // then
        String expected = "{  \"profile\": {    \"dev\": {      \"paymentGateway\": {        \"domain\": \"http://dev-pg.com\",        \"propertyPath\": \"classpath:conf/dev-pg.properties\"      }    },    \"prod\": {      \"paymentGateway\": {        \"domain\": \"http://pg.com\",        \"propertyPath\": \"classpath:conf/dev-pg.properties\"      }    }  },  \"googleMaps\" : {    \"url\" : \"http://google.com/maps\"  }}";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTextForAepConfig() throws IOException {
        // given
        AppConfigFileLoader appConfigFileLoader = new AppConfigFileLoader("conf/aep-config.json");

        // when
        String actual = appConfigFileLoader.getText();

        // then
        String expected = "{  \"KindOfProfileName\" : [\"local\", \"dev\", \"stag\", \"prod\"]}";
        Assert.assertEquals(expected, actual);
    }
}