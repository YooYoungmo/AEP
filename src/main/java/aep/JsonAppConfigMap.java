package aep;

import java.util.Map;

/**
 * Created by yooyoung-mo on 2017. 2. 10..
 */
public class JsonAppConfigMap {
    private final String json;
    private Map configMap;

    public JsonAppConfigMap(String json) {
        this.json = json;
        this.configMap = parseJson(json);
    }

    private Map parseJson(String json) {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(json);
    }

    public JsonAppConfigMap get(String key) {
        if(configMap != null) {
            this.configMap = (Map)this.configMap.get(key);
        }

        return this;
    }

    public Map getConfigMap() {
        return configMap;
    }

    public void initMap() {
        this.configMap = parseJson(json);
    }
}
