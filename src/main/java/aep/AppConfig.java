package aep;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        InputStream configFile = getAppConfigFile();
        JSONObject configJson = convertToJson(configFile);

        JSONObject profile = (JSONObject)configJson.get(CONFIG_PROFILE_ELEMENT);
        String appEnvProfileActive = System.getProperty(SYSTEM_PROPERTY_APP_ENV_PROFILE_ACTIVE);
        JSONObject dev = (JSONObject)profile.get(appEnvProfileActive);
        JSONObject contents = (JSONObject)dev.get(id);


        Map<String, String> configMap = new HashMap<String, String>();
        Set<Map.Entry<String, String>> set = contents.entrySet();
        for(Map.Entry<String, String> o : set) {
            configMap.put(o.getKey(), o.getValue());
        }
        return configMap;
    }

    private static JSONObject convertToJson(InputStream configFile) throws IOException {
        BufferedReader bR = new BufferedReader(new InputStreamReader(configFile));
        String line = "";

        StringBuilder responseStrBuilder = new StringBuilder();
        while ((line = bR.readLine()) != null) {

            responseStrBuilder.append(line);
        }
        configFile.close();

        return JSONObject.fromObject(responseStrBuilder.toString());

    }

    private static InputStream getAppConfigFile() {
        return AppConfig.class.getClassLoader().getResourceAsStream(CONFIG_ROOT_DEFAULT_FILE_PATH);
    }
}
