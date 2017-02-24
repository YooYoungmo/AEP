package aep;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-09.
 */
public class AppConfig {
    public static Map<String, String> getConfigValue(String key) throws IOException {

        InputStream configStream = AppConfig.class.getClassLoader().getResourceAsStream("conf/app-config.json");

        BufferedReader configBufferReader = new BufferedReader(new InputStreamReader(configStream));
        String line = "";

        StringBuilder configStringBuilder = new StringBuilder();
        while ((line = configBufferReader.readLine()) != null) {

            configStringBuilder.append(line);
        }
        configStream.close();

        String activeProfile = System.getProperty("app.env.profile.active");
        JSONObject configJSONObject = JSONObject.fromObject(configStringBuilder.toString());

        JSONObject profileJSONObject = (JSONObject) configJSONObject.get("profile");
        JSONObject stageJSONObject = (JSONObject) profileJSONObject.get("stage");
        JSONObject devJSONObject = (JSONObject) stageJSONObject.get(activeProfile);
        JSONObject keyJSONObject = (JSONObject) devJSONObject.get(key);

        return keyJSONObject;
    }
}
