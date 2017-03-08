package aep;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-09.
 */
public class AppConfig {
    public static Map<String, String> getConfigValue(String key) throws IOException {

        String appConfigText = AppConfigFileLoader.getText();

        JSONObject configJSONObject = JSONObject.fromObject(appConfigText);
        JSONObject profileJSONObject = (JSONObject) configJSONObject.get("profile");
        JSONArray validStageJSONArray = profileJSONObject.getJSONArray("validStage");

        String activeProfile = System.getProperty("app.env.profile.active");
        if (!validStageJSONArray.contains(activeProfile)) {
            throw new InvalidActiveProfileException();
        }

        JSONObject stageJSONObject = (JSONObject) profileJSONObject.get("stage");
        JSONObject configValuesJSONObject = (JSONObject) stageJSONObject.get(activeProfile);
        JSONObject configValueJSONObject = (JSONObject) configValuesJSONObject.get(key);

        if (configValueJSONObject == null) {
            throw new NotFoundConfigValueException();
        }

        return configValueJSONObject;
    }
}
