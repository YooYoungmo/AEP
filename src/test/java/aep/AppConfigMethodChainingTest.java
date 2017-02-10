package aep;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by 이영호 on 2017-02-10.
 */
public class AppConfigMethodChainingTest {

    @Test
    public void get(){
        // given
        String configText = "{  \"profile\": {    \"validStage\" : [\"dev\", \"stag\", \"prod\"],    \"stage\" : {      \"dev\": {        \"paymentGateway\": {          \"domain\": \"http://dev-pg.com\", \"company\" : {\n            \"name\" : \"dev-pg\"\n            , \"contract\" : \"pg@example.com\"\n          },          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        }      },      \"stag\": {        \"paymentGateway\": {          \"domain\": \"http://stg-pg.com\",          \"propertyPath\": \"classpath:conf/stg-pg.properties\"        }      },      \"prod\": {        \"paymentGateway\": {          \"domain\": \"http://pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        },        \"googleMaps\" : {          \"url\" : \"http://googletest.com/maps\"        }      }    }  },  \"googleMaps\" : {    \"url\" : \"http://google.com/maps\"  },  \"openApi\" : {    \"url\" :\"http://daum.net\"  }}";
        String id = "openApi";
        // String key = "url";

        // when
        AppConfigMethodChaining appConfigMethodChaining = new AppConfigMethodChaining(configText);
        AppConfigMethodChaining appConfigMethodChainingAfter = appConfigMethodChaining.get(id);

        // then
        Assert.assertEquals(appConfigMethodChaining, appConfigMethodChainingAfter);
    }

    @Test
    public void getValue(){
        // given
        String configText = "{  \"profile\": {    \"validStage\" : [\"dev\", \"stag\", \"prod\"],    \"stage\" : {      \"dev\": {        \"paymentGateway\": {          \"domain\": \"http://dev-pg.com\", \"company\" : {\n            \"name\" : \"dev-pg\"\n            , \"contract\" : \"pg@example.com\"\n          },          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        }      },      \"stag\": {        \"paymentGateway\": {          \"domain\": \"http://stg-pg.com\",          \"propertyPath\": \"classpath:conf/stg-pg.properties\"        }      },      \"prod\": {        \"paymentGateway\": {          \"domain\": \"http://pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        },        \"googleMaps\" : {          \"url\" : \"http://googletest.com/maps\"        }      }    }  },  \"googleMaps\" : {    \"url\" : \"http://google.com/maps\"  },  \"openApi\" : {    \"url\" :\"http://daum.net\"  }}";
        String id = "openApi";
        String key = "url";

        // when
        String expected = "http://daum.net";
        AppConfigMethodChaining appConfigMethodChaining = new AppConfigMethodChaining(configText);
        String actual = appConfigMethodChaining.get(id).get(key).getValue();

        // then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getValue_nestedConfig(){
        // given
        String configText = "{  \"profile\": {    \"validStage\" : [\"dev\", \"stag\", \"prod\"],    \"stage\" : {      \"dev\": {        \"paymentGateway\": {          \"domain\": \"http://dev-pg.com\", \"company\" : {\n            \"name\" : \"dev-pg\"\n            , \"contract\" : \"pg@example.com\"\n          },          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        }      },      \"stag\": {        \"paymentGateway\": {          \"domain\": \"http://stg-pg.com\",          \"propertyPath\": \"classpath:conf/stg-pg.properties\"        }      },      \"prod\": {        \"paymentGateway\": {          \"domain\": \"http://pg.com\",          \"propertyPath\": \"classpath:conf/dev-pg.properties\"        },        \"googleMaps\" : {          \"url\" : \"http://googletest.com/maps\"        }      }    }  },  \"googleMaps\" : {    \"url\" : \"http://google.com/maps\"  },  \"openApi\" : {    \"url\" :\"http://daum.net\"  }}";
        String profileRoot = "profile";
        String stage = "stage";
        String configProfile = "dev";
        String id = "paymentGateway";
        String key1 = "company";
        String key2 = "contract";

        // when
        String expected = "pg@example.com";
        AppConfigMethodChaining appConfigMethodChaining = new AppConfigMethodChaining(configText);
        String actual = appConfigMethodChaining.get(profileRoot).get(stage).get(configProfile).get(id).get(key1).get(key2).getValue();

        // then
        Assert.assertEquals(expected, actual);
    }
}
