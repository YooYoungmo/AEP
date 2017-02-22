package aep;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-09.
 */
public class AppConfig {
    public static Map<String, String> getConfigValue(String paymentGateway) {

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("propertyPath", "classpath:conf/dev-pg.properties");

        return hashMap;
    }
}
