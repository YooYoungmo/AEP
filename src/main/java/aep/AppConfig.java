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
    private static JsonAppConfigMap appConfigMap = null;

    private AppConfig() {

    }
    public static Map getConfigValue(String key) throws IOException {
        loadResource();
        appConfigMap.initMap();
        if(isActiveProfile()) {
            final String activeProfile = getActiveProfile();

            validateActiveProfile(activeProfile);

            Map resultMap = getConfigValueInActiveProfile(activeProfile, key);

            if(resultMap == null) {
                resultMap = getConfigValueInDefault(key);
            }

            if(resultMap == null) {
                throw new IllegalArgumentException(key + "가 존재하지 않습니다.");
            }

            return resultMap;

        } else {
            throw new SystemPropertyNotFoundException();
        }
    }

    private synchronized static void loadResource() throws IOException {
        if(appConfigMap == null) {
            AppConfigFileLoader configFileLoader = AppConfigFileLoader.getInstance();
            String configText = configFileLoader.getText();
            appConfigMap = new JsonAppConfigMap(configText);
        }
    }

    private static Map getConfigValueInDefault(String key) {
        appConfigMap.initMap();

        return appConfigMap.get("default").get(key).getConfigMap();
    }

    private static Map getConfigValueInActiveProfile(String activeProfile, String key) {
        appConfigMap.initMap();
        Map resultMap = appConfigMap.get(CONFIG_PROFILE_ELEMENT).get("stage")
                .get(activeProfile).get(key).getConfigMap();

        return resultMap;
    }

    private static void validateActiveProfile(final String activeProfile) {
        List<String> validStage = (List<String>)appConfigMap.get(CONFIG_PROFILE_ELEMENT)
                .getConfigMap().get("validStage");

        String appEnvProfileActive = getActiveProfile();
        if(!validStage.contains(appEnvProfileActive)){
            throw new SystemPropertyInvalidValueException();
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
