package aep;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        if(configMap != null) {
            Map<String, String> resultMap = new HashMap<String, String>();
            Set<Map.Entry<String, String>> set = configMap.entrySet();
            for(Map.Entry<String, String> o : set) {
                resultMap.put(o.getKey(), o.getValue());
            }
            initMap();
            return resultMap;
        } else {
            return null;
        }
    }

    private void initMap() {
        this.configMap = parseJson(json);
    }
}
