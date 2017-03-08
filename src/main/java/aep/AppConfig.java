package aep;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <a href="https://github.com/YooYoungmo/AEP">AEP Github README.md</a> 참조
 * Utility 클래스의 형태로 상속하거나 new 키워드를 통해 생성하여 사용 않는다.
 *
 * JSP(Java Server Page) 커스텀 태그 구현체는 {@link aep.tag.AppConfigTag} 참조
 */
public final class AppConfig {
    private static final String CONFIG_PROFILE_ELEMENT = "profile";
    private static final String SYSTEM_PROPERTY_APP_ENV_PROFILE_ACTIVE = "app.env.profile.active";
    private static JsonAppConfigMap appConfigMap = null;

    /**
     * <pre>
     * Utility 클래스의 형태(static 메소드의 집합)이기 때문에
     * new 키워드를 사용을 방지 하기 위해 생성자를 private 선언
     * </pre>
     */
    private AppConfig() {
    }

    /**
     * <a href="https://github.com/YooYoungmo/AEP#사용법">Java 사용법</a>을 참조
     *
     * @param key
     * @return
     * @throws IOException
     */
    public static Map getConfigValue(String key) throws IOException {
        loadResource();
        if(isActiveProfile()) {
            final String activeProfile = getActiveProfile();

            validateActiveProfile(activeProfile);

            Map resultMap = getConfigValueInActiveProfile(activeProfile, key);

            if(resultMap.isEmpty()) {
                resultMap = getConfigValueInDefault(key);
            }

            if(resultMap.isEmpty()) {
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
        return appConfigMap.get("default").get(key).getConfigMap();
    }

    private static Map getConfigValueInActiveProfile(String activeProfile, String key) {
        Map resultMap = appConfigMap.get(CONFIG_PROFILE_ELEMENT).get("stage")
                .get(activeProfile).get(key).getConfigMap();

        return resultMap;
    }

    private static void validateActiveProfile(final String activeProfile) {
        Map map = appConfigMap.get(CONFIG_PROFILE_ELEMENT)
                .getConfigMap();

        List<String> validStage = (List<String>)map.get("validStage");

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
