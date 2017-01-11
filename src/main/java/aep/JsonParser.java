package aep;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by yooyoung-mo on 2017. 1. 10..
 */
public class JsonParser {
    public Map<String, Map<String, Map<String, Map<String, String>>>> parseForProfile(String json) {
        return JSONObject.fromObject(json);
    }

    public Map<String, Map<String, String>> parseForNoneProfile(String json) {
        return JSONObject.fromObject(json);
    }
}
