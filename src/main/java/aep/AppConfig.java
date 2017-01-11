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

        Map<String, String> targetMap = null;
        JsonParser parser = new JsonParser();
        if(isActiveProfile()) {
            String appEnvProfileActive = getActiveProfile();
            Map<String, Map<String, Map<String, Map<String, String>>>> configMap = parser.parseForProfile(configText);

            Map<String, Map<String, String>> envMap = configMap.get(CONFIG_PROFILE_ELEMENT).get(appEnvProfileActive);

            if(envMap == null) {
                throw new ProfileNotFoundException();
            }

            targetMap = envMap.get(id);

        } else {
            Map<String, Map<String, String>> configMap = parser.parseForNoneProfile(configText);
            targetMap = configMap.get(id);
        }

        if(targetMap == null) {
            throw new IllegalArgumentException(id + "가 존재하지 않습니다.");
        }

        Map<String, String> resultMap = new HashMap<String, String>();
        Set<Map.Entry<String, String>> set = targetMap.entrySet();
        for(Map.Entry<String, String> o : set) {
            resultMap.put(o.getKey(), o.getValue());
        }
        return resultMap;
    }

    private static String getActiveProfile() {
        return System.getProperty(SYSTEM_PROPERTY_APP_ENV_PROFILE_ACTIVE);
    }

    private static boolean isActiveProfile() {
        String activeProfile = getActiveProfile();
        if(activeProfile == null || activeProfile.length() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
