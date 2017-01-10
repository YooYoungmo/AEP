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
        String expected = "{  \"profile\": {    \"dev\": {      \"paymentGateway\": {        \"domain\": \"http://dev-pg.com\",        \"propertyPath\": \"classpath:conf/dev-pg.properties\"      }    },    \"prod\": {      \"paymentGateway\": {        \"domain\": \"http://pg.com\",        \"propertyPath\": \"classpath:conf/dev-pg.properties\"      }    }  },  \"googleMaps\" : {    \"url\" : \"http://google.com/maps\"  }}";
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

}