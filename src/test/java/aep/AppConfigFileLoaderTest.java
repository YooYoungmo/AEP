package aep;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by langve on 2017. 3. 3..
 */
public class AppConfigFileLoaderTest {
    @Test
    public void getText() throws Exception {
        //given

        //when
        String actual = AppConfigFileLoader.getText();
        String expected = "{  \"default\" : {    \"googleMaps\" : {      \"url\" : \"http://google.com/maps\"    },    \"openApi\" : {      \"url\" :\"http://daum.net\"    }  },  \"profile\": {    \"validStage\" : [\"dev\", \"stag\", \"prod\"],    \"stage\" : {      \"dev\": {        \"paymentGateway\": {          \"domain\": \"http://dev-pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        }      },      \"stag\": {        \"paymentGateway\": {          \"domain\": \"http://stg-pg.com\",          \"propertyPath\": \"classpath:conf/stg-pg.properties\"        }      },      \"prod\": {        \"paymentGateway\": {          \"domain\": \"http://prod-pg.com\",          \"propertyPath\": \"classpath:conf/prod-pg.properties\"        },        \"googleMaps\" : {          \"url\": \"http://prod-google.com/maps\"        }      }    }  }}";

        //then
        Assert.assertEquals(expected, actual);
    }

}