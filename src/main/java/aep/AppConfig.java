package aep;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
        AppConfigFileLoader configFileLoader = new AppConfigFileLoader(CONFIG_ROOT_DEFAULT_FILE_PATH);
        String configText = configFileLoader.getText();

        AppConfigFileLoader aepConfigFileLoader = new AppConfigFileLoader("conf/aep-config.json");
        String aepConfigText = aepConfigFileLoader.getText();

        JsonParser parser = new JsonParser();

        Map<String, List<String>> validProfileMap = parser.parse(aepConfigText);
        List<String> listValidProfile = validProfileMap.get("KindOfProfileName");

        Map<String, String> targetMap;

        Map configMap = parser.parse(configText);
        String appEnvProfileActive = getActiveProfile();

        if(!listValidProfile.contains(appEnvProfileActive)){
            throw new SystemPropertyInvalidValueException();
        }

        Map<String, Map<String, String>> profileMap =
                ((Map<String, Map<String, Map<String, Map<String, String>>>>)configMap)
                        .get(CONFIG_PROFILE_ELEMENT)
                        .get(appEnvProfileActive);

        if(profileMap == null) {
            throw new ProfileNotFoundException();
        }
        targetMap = profileMap.get(id);

        if(targetMap == null) {
            targetMap = ((Map<String, Map<String, String>>)configMap).get(id);
        }
        return targetMap;
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
