package aep;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yooyoung-mo on 2017. 2. 10..
 */
public class JsonAppConfigMap {
    private Map configMap;
    private final Map initialConfigMap;

    public JsonAppConfigMap(String json) {
        this.initialConfigMap = parseJson(json);
        this.configMap = new HashMap(initialConfigMap);
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
        Map resultMap = null;

        if(configMap != null) {
            resultMap = new HashMap(configMap);
        }

        initMap();
        return resultMap;
    }

    private void initMap() {
        this.configMap = new HashMap(this.initialConfigMap);
    }
}
