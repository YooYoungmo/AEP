package aep;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-09.
 */
public class AppConfig {
    public static Map<String, String> getConfigValue(String key) throws IOException {

        InputStream configStream = AppConfig.class.getClassLoader().getResourceAsStream("conf/app-config.json");
        Closeable streamCloser = configStream;
        StringBuilder configStringBuilder = new StringBuilder();

        try {
            BufferedReader configBufferReader = new BufferedReader(new InputStreamReader(configStream));
            streamCloser = configBufferReader;

            String line = "";

            while ((line = configBufferReader.readLine()) != null) {
                configStringBuilder.append(line);
            }
        } finally {
            streamCloser.close();
        }

        String activeProfile = System.getProperty("app.env.profile.active");
        JSONObject configJSONObject = JSONObject.fromObject(configStringBuilder.toString());

        JSONObject profileJSONObject = (JSONObject) configJSONObject.get("profile");
        JSONArray validStageJSONArray = profileJSONObject.getJSONArray("validStage");

        if (!validStageJSONArray.contains(activeProfile)) {
            throw new InvalidActiveProfileException();
        }

        JSONObject stageJSONObject = (JSONObject) profileJSONObject.get("stage");
        JSONObject devJSONObject = (JSONObject) stageJSONObject.get(activeProfile);
        JSONObject keyJSONObject = (JSONObject) devJSONObject.get(key);

        return keyJSONObject;
    }
}
