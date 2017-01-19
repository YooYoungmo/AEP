package aep;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
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

    @Test
    public void parse_getArrary(){
        // given
        String json = "{\"KindOfProfileName\" : [\"local\", \"dev\", \"stag\", \"prod\"]}";
        JsonParser jsonParser = new JsonParser();

        // when
        Map<String, List<String>> actual = jsonParser.parse(json);

        // then
        Assert.assertNotNull(actual.get("KindOfProfileName"));

        final int sizeOfKindProfileName = 4;
        Assert.assertEquals(sizeOfKindProfileName, actual.get("KindOfProfileName").size());

        Assert.assertEquals("local", actual.get("KindOfProfileName").get(0));
        Assert.assertEquals("dev", actual.get("KindOfProfileName").get(1));
        Assert.assertEquals("stag", actual.get("KindOfProfileName").get(2));
        Assert.assertEquals("prod", actual.get("KindOfProfileName").get(3));
    }

}