package aep;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by 유영모 on 2017-02-10.
 */
public class AppConfigMapTest {

    @Test
    public void getMap() {
        // given
        String configText = "{  \"profile\": {    \"validStage\" : [\"dev\", \"stag\", \"prod\"],    \"stage\" : {      \"dev\": {        \"paymentGateway\": {          \"domain\": \"http://dev-pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        }      },      \"stag\": {        \"paymentGateway\": {          \"domain\": \"http://stg-pg.com\",          \"propertyPath\": \"classpath:conf/stg-pg.properties\"        }      },      \"prod\": {        \"paymentGateway\": {          \"domain\": \"http://pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        },        \"googleMaps\" : {          \"url\" : \"http://googletest.com/maps\"        }      }    }  },  \"googleMaps\" : {    \"url\" : \"http://google.com/maps\"  },  \"openApi\" : {    \"url\" :\"http://daum.net\"  }}";

        // when
        AppConfigMap appConfigMap = new AppConfigMap(configText);
        Map paymentGateway = appConfigMap.get("profile").get("stage").get("dev").get("paymentGateway").getMap();

        // then
        Assert.assertEquals("http://dev-pg.com", paymentGateway.get("domain"));
        Assert.assertEquals("classpath:conf/dev-pg.properties", paymentGateway.get("propertyPath"));
    }

    @Test
    public void getString() {
        // given
        String configText = "{  \"profile\": {    \"validStage\" : [\"dev\", \"stag\", \"prod\"],    \"stage\" : {      \"dev\": {        \"paymentGateway\": {          \"domain\": \"http://dev-pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        }      },      \"stag\": {        \"paymentGateway\": {          \"domain\": \"http://stg-pg.com\",          \"propertyPath\": \"classpath:conf/stg-pg.properties\"        }      },      \"prod\": {        \"paymentGateway\": {          \"domain\": \"http://pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        },        \"googleMaps\" : {          \"url\" : \"http://googletest.com/maps\"        }      }    }  },  \"googleMaps\" : {    \"url\" : \"http://google.com/maps\"  },  \"openApi\" : {    \"url\" :\"http://daum.net\"  }}";

        // when
        AppConfigMap appConfigMap = new AppConfigMap(configText);
        String domain = appConfigMap.get("profile").get("stage").get("dev").get("paymentGateway").getString("domain");

        // then
        Assert.assertEquals("http://dev-pg.com", domain);
    }
}