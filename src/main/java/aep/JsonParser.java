package aep;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by yooyoung-mo on 2017. 1. 10..
 */
public class JsonParser {
    public Map parse(String json) {
        return JSONObject.fromObject(json);
    }
}
