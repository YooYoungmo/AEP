package aep;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by yooyoung-mo on 2017. 1. 10..
 */
public class JsonParserTest {

    @Test
    public void parse(){
        // given
        String json = "{  \"profile\": {    \"dev\": {      \"paymentGateway\": {        \"domain\": \"http://dev-pg.com\",        \"propertyPath\": \"classpath:conf/dev-pg.properties\"      }    },    \"prod\": {      \"paymentGateway\": {        \"domain\": \"http://pg.com\",        \"propertyPath\": \"classpath:conf/dev-pg.properties\"      }    }  },  \"googleMaps\" : {    \"url\" : \"http://google.com/maps\"  }}";
        JsonParser jsonParser = new JsonParser();

        // when
        Map<String, Map<String, Map<String, Map<String, String>>>> actual = jsonParser.parse(json);

        // then
        Assert.assertNotNull(actual);
        Assert.assertNotNull(actual.get("profile").get("dev").get("paymentGateway"));
    }
}