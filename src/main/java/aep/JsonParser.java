package aep;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by yooyoung-mo on 2017. 1. 10..
 */
public final class JsonParser {

    /**
     * 문자열을 Map 형태로 전환한다.
     * @param json Json 문자열
     * @return Map
     */
    public Map parse(final String json) {
        return JSONObject.fromObject(json);
    }
}
