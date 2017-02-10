package aep;

import java.util.Map;

/**
 * Created by 이영호 on 2017-02-10.
 */
public class AppConfigMethodChaining {
    private Object value;

    public AppConfigMethodChaining(String configText) {
        JsonParser jsonParser = new JsonParser();
        value = jsonParser.parse(configText);
    }

    public AppConfigMethodChaining get(String id) {
        this.value = ((Map) value).get(id);

        return this;
    }

    public final String getValue() {
        return ((String) value);
    }
}
