package aep;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 유영모 on 2017-01-09.
 */
public class AppConfig {
    private static final String CONFIG_ROOT_DEFAULT_FILE_PATH = "conf/app-config.json";
    private static final String CONFIG_PROFILE_ELEMENT = "profile";
    private static final String SYSTEM_PROPERTY_APP_ENV_PROFILE_ACTIVE = "app.env.profile.active";

    public static Map<String, String> get(String id) throws IOException {
        AppConfigFileLoader configFileLoader = new AppConfigFileLoader(CONFIG_ROOT_DEFAULT_FILE_PATH);
        String configText = configFileLoader.getText();

        JsonParser parser = new JsonParser();
        Map<String, Map<String, Map<String, Map<String, String>>>> configMap = parser.parse(configText);

        String appEnvProfileActive = System.getProperty(SYSTEM_PROPERTY_APP_ENV_PROFILE_ACTIVE);
        Map<String, String> targetMap = configMap.get(CONFIG_PROFILE_ELEMENT).get(appEnvProfileActive).get(id);
        Map<String, String> resultMap = new HashMap<String, String>();
        Set<Map.Entry<String, String>> set = targetMap.entrySet();
        for(Map.Entry<String, String> o : set) {
            resultMap.put(o.getKey(), o.getValue());
        }
        return resultMap;
    }
}
