package aep;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-09.
 */
public final class AppConfig {
    private static final String CONFIG_PROFILE_ELEMENT = "profile";
    private static final String SYSTEM_PROPERTY_APP_ENV_PROFILE_ACTIVE = "app.env.profile.active";

    private AppConfig() {
    }

    public static Map getConfigValue(String key) throws IOException {
        // 1. File Load
        AppConfigFileLoader configFileLoader = AppConfigFileLoader.getInstance();
        String configText = configFileLoader.getText();
        JsonAppConfigMap appConfigMap = new JsonAppConfigMap(configText);

        if(isActiveProfile()) {
            // 2. activeProfile validation
            List<String> validStage = (List<String>)appConfigMap.get(CONFIG_PROFILE_ELEMENT)
                    .getConfigMap().get("validStage");

            String appEnvProfileActive = getActiveProfile();
            if(!validStage.contains(appEnvProfileActive)){
                throw new SystemPropertyInvalidValueException();
            }

            // 3. getConfigValue()
            appConfigMap.initMap();
            Map resultMap = appConfigMap.get(CONFIG_PROFILE_ELEMENT).get("stage")
                    .get(appEnvProfileActive).get(key).getConfigMap();

            if(resultMap == null) {
                appConfigMap.initMap();
                resultMap = appConfigMap.get("default").get(key).getConfigMap();
            }

            if(resultMap == null) {
                throw new IllegalArgumentException(key + "가 존재하지 않습니다.");
            }

            return resultMap;

        } else {
            throw new SystemPropertyNotFoundException();
        }
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
