package aep;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by 유영모 on 2017-01-09.
 */
public class AppConfig {
    public static Map<String, String> getConfigValue(String key) {

        String configText = "{\n  \"default\" : {\n    \"googleMaps\" : {\n      \"url\" : \"http://google.com/maps\"\n    },\n    \"openApi\" : {\n      \"url\" :\"http://daum.net\"\n    }\n  },\n  \"profile\": {\n    \"validStage\" : [\"dev\", \"stag\", \"prod\"],\n    \"stage\" : {\n      \"dev\": {\n        \"paymentGateway\": {\n          \"domain\": \"http://dev-pg.com\",\n          \"propertyPath\": \"classpath:conf/dev-pg.properties\"\n        }\n      },\n      \"stag\": {\n        \"paymentGateway\": {\n          \"domain\": \"http://stg-pg.com\",\n          \"propertyPath\": \"classpath:conf/stg-pg.properties\"\n        }\n      },\n      \"prod\": {\n        \"paymentGateway\": {\n          \"domain\": \"http://pg.com\",\n          \"propertyPath\": \"classpath:conf/dev-pg.properties\"\n        },\n        \"googleMaps\" : {\n          \"url\" : \"http://googletest.com/maps\"\n        }\n      }\n    }\n  }\n}";

        JSONObject configJSONObject = JSONObject.fromObject(configText);

        JSONObject profileJSONObject = (JSONObject) configJSONObject.get("profile");
        JSONObject stageJSONObject = (JSONObject) profileJSONObject.get("stage");
        JSONObject devJSONObject = (JSONObject) stageJSONObject.get("dev");
        JSONObject keyJSONObject = (JSONObject) devJSONObject.get(key);

        return keyJSONObject;
    }
}
