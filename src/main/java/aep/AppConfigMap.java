package aep;

import java.util.Map;

/**
 * Created by 유영모 on 2017-02-10.
 */
public class AppConfigMap {
    private Map configMap;

    public AppConfigMap(String configText) {
        JsonParser parser = new JsonParser();
        configMap = parser.parse(configText);
    }

    public AppConfigMap get(String key) {
        configMap = (Map)configMap.get(key);
        return this;
    }

    public Map getMap() {
        return configMap;
    }

    public String getString(String key) {
        return (String)configMap.get(key);
    }
}
