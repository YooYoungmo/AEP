package aep;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Json 문자열을 파싱하여 Java Collection 으로 만들어
 * {@link Map} 인터페이스로 가공하는 역할을 한다.
 *
 * 프로젝트 제약 사항으로 JSON-lib 라이브러를 사용하였음.
 * Created by yooyoung-mo on 2017. 1. 10..
 */
public final class JsonParser {

    /**
     * 문자열을 {@link Map} 형태로 전환한다.
     * @param json Json 문자열
     * @return {@link Map}
     */
    public Map parse(final String json) {
        return JSONObject.fromObject(json);
    }
}
