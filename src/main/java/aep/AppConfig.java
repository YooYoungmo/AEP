package aep;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 유영모 on 2017-01-09.
 */
public final class AppConfig {
    private static final String CONFIG_PROFILE_ELEMENT = "profile";
    private static final String SYSTEM_PROPERTY_APP_ENV_PROFILE_ACTIVE = "app.env.profile.active";

    private AppConfig() {
    }

    public static Map<String, String> get(String id) throws IOException {

        Map<String, String> configMap = null;

        if(isActiveProfile()) {
            configMap = getConfigMap(id);
        } else {
            throw new SystemPropertyNotFoundException();
        }

        if(configMap == null) {
            throw new IllegalArgumentException(id + "가 존재하지 않습니다.");
        }

        Map<String, String> resultMap = new HashMap<String, String>();
        Set<Map.Entry<String, String>> set = configMap.entrySet();
        for(Map.Entry<String, String> o : set) {
            resultMap.put(o.getKey(), o.getValue());
        }
        return resultMap;
    }

    private static Map<String, String> getConfigMap(String id) throws IOException {
        // 1. File Load
        AppConfigFileLoader configFileLoader = AppConfigFileLoader.getInstance();
        String configText = configFileLoader.getText();

        // 2. Json Parse
        JsonParser parser = new JsonParser();
        Map configMap = parser.parse(configText);

        // 4. activeProfile validation
        List<String> validStage = ((Map<String, Map<String, List<String>>>)configMap).get(CONFIG_PROFILE_ELEMENT)
                .get("validStage");

        String appEnvProfileActive = getActiveProfile();

        if(!validStage.contains(appEnvProfileActive)){
            throw new SystemPropertyInvalidValueException();
        }

        // 5. get()
        Map<String, Map<String, String>> profileMap =
                ((Map<String, Map<String, Map<String, Map<String, Map<String, String>>>>>)configMap)
                        .get(CONFIG_PROFILE_ELEMENT)
                        .get("stage")
                        .get(appEnvProfileActive);

        if(profileMap == null) {
            throw new ProfileNotFoundException();
        }

        Map<String, String> targetMap = profileMap.get(id);

        if(targetMap == null) {
            targetMap = ((Map<String, Map<String, String>>)configMap).get(id);
        }
        return targetMap;
    }

    private static String getActiveProfile() {
        return System.getProperty(SYSTEM_PROPERTY_APP_ENV_PROFILE_ACTIVE);
    }

    private static boolean isActiveProfile() {
        boolean result = false;

        String activeProfile = getActiveProfile();
        if(activeProfile != null && activeProfile.length() > 0) {
            result = true;
        }

        return result;
    }
}
