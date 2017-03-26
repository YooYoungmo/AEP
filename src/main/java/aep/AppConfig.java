package aep;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * Created by langve on 2017-02-22.
 */
public class AppConfig {
    private static JSONObject appConfigJSONObject;

    public static Map<String, String> getConfigValue(String key) throws IOException {

        String appConfigText = AppConfigFileLoader.getText();

        JSONObject configJSONObject = parseJSONObject(appConfigText);
        JSONObject profileJSONObject = (JSONObject) configJSONObject.get("profile");
        JSONArray validStageJSONArray = profileJSONObject.getJSONArray("validStage");

        String activeProfile = getActiveProfile();

        if (!validActiveProfile()) {
            throw new InvalidActiveProfileException();
        }

        JSONObject stageJSONObject = (JSONObject) profileJSONObject.get("stage");
        JSONObject configValuesJSONObject = (JSONObject) stageJSONObject.get(activeProfile);
        JSONObject configValueJSONObject = (JSONObject) configValuesJSONObject.get(key);

        if (configValueJSONObject == null) {
            JSONObject defaultJSONObject = (JSONObject) configJSONObject.get("default");
            configValueJSONObject = (JSONObject) defaultJSONObject.get(key);
        }

        if (configValueJSONObject == null) {
            throw new NotFoundConfigValueException();
        }

        return configValueJSONObject;
    }

    public static String getActiveProfile() {
        return System.getProperty("app.env.profile.active");
    }

    public static boolean validActiveProfile() throws IOException {

        String appConfigText = AppConfigFileLoader.getText();

        JSONObject configJSONObject = parseJSONObject(appConfigText);
        JSONObject profileJSONObject = (JSONObject) configJSONObject.get("profile");
        JSONArray validStageJSONArray = profileJSONObject.getJSONArray("validStage");

        String activeProfile = getActiveProfile();

        return validStageJSONArray.contains(activeProfile);
    }

    public static JSONObject parseJSONObject(String appConfigText) {
        try {
            if (appConfigJSONObject == null)
                appConfigJSONObject = JSONObject.fromObject(appConfigText);
        } catch (JSONException jsonException) {
            throw new WrongFormatAppConfigTextException();
        }

        return appConfigJSONObject;
    }
}
