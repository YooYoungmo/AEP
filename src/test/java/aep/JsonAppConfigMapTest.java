package aep;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;


/**
 * Created by yooyoung-mo on 2017. 2. 10..
 */
public class JsonAppConfigMapTest {
    @Test
    public void getMap_configProfile에없는경우() {
        // given
        String json = "{  \"profile\": {    \"validStage\" : [\"dev\", \"stag\", \"prod\"],    \"stage\" : {      \"dev\": {        \"paymentGateway\": {          \"domain\": \"http://dev-pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        }      },      \"stag\": {        \"paymentGateway\": {          \"domain\": \"http://stg-pg.com\",          \"propertyPath\": \"classpath:conf/stg-pg.properties\"        }      },      \"prod\": {        \"paymentGateway\": {          \"domain\": \"http://pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        },        \"googleMaps\" : {          \"url\" : \"http://googletest.com/maps\"        }      }    }  },  \"googleMaps\" : {    \"url\" : \"http://google.com/maps\"  },  \"openApi\" : {    \"url\" :\"http://daum.net\"  }}";

        String key = "openApi";

        // when
        JsonAppConfigMap appConfigMap = new JsonAppConfigMap(json);
        Map openApi = appConfigMap.get(key).getConfigMap();

        // then
        Assert.assertEquals("http://daum.net", openApi.get("url"));
    }

    @Test
    public void getMap_activeProfile에있는경우() {
        // given
        String json = "{  \"profile\": {    \"validStage\" : [\"dev\", \"stag\", \"prod\"],    \"stage\" : {      \"dev\": {        \"paymentGateway\": {          \"domain\": \"http://dev-pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        }      },      \"stag\": {        \"paymentGateway\": {          \"domain\": \"http://stg-pg.com\",          \"propertyPath\": \"classpath:conf/stg-pg.properties\"        }      },      \"prod\": {        \"paymentGateway\": {          \"domain\": \"http://pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        },        \"googleMaps\" : {          \"url\" : \"http://googletest.com/maps\"        }      }    }  },  \"googleMaps\" : {    \"url\" : \"http://google.com/maps\"  },  \"openApi\" : {    \"url\" :\"http://daum.net\"  }}";

        // when
        JsonAppConfigMap appConfigMap = new JsonAppConfigMap(json);
        Map paymentGateway = appConfigMap.get("profile").get("stage").get("dev").get("paymentGateway").getConfigMap();

        // then
        Assert.assertEquals("http://dev-pg.com", paymentGateway.get("domain"));
        Assert.assertEquals("classpath:conf/dev-pg.properties", paymentGateway.get("propertyPath"));
    }

    @Test
    public void initMap() {
        // given
        String json = "{  \"profile\": {    \"validStage\" : [\"dev\", \"stag\", \"prod\"],    \"stage\" : {      \"dev\": {        \"paymentGateway\": {          \"domain\": \"http://dev-pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        }      },      \"stag\": {        \"paymentGateway\": {          \"domain\": \"http://stg-pg.com\",          \"propertyPath\": \"classpath:conf/stg-pg.properties\"        }      },      \"prod\": {        \"paymentGateway\": {          \"domain\": \"http://pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        },        \"googleMaps\" : {          \"url\" : \"http://googletest.com/maps\"        }      }    }  },  \"googleMaps\" : {    \"url\" : \"http://google.com/maps\"  },  \"openApi\" : {    \"url\" :\"http://daum.net\"  }}";

        // when
        JsonAppConfigMap appConfigMap = new JsonAppConfigMap(json);
        Map paymentGateway = appConfigMap.get("profile").getConfigMap();

        paymentGateway = appConfigMap.get("profile").get("stage").get("dev").get("paymentGateway").getConfigMap();

        // then
        Assert.assertEquals("http://dev-pg.com", paymentGateway.get("domain"));
        Assert.assertEquals("classpath:conf/dev-pg.properties", paymentGateway.get("propertyPath"));
    }
}
