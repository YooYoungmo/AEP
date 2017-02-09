package aep.tag;

import aep.AppConfig;
import org.junit.Assert;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Map;

/**
 * Created by yooyoung-mo on 2017. 2. 9..
 */
public class AppConfigTag extends SimpleTagSupport {

    private String id;
    private String key;

    public AppConfigTag() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void doTag() throws JspException, IOException {
        Map<String, String> configMap = AppConfig.get(this.id);

        String value = configMap.get(this.key);

        final JspWriter out = getJspContext().getOut();
        out.println( value );
    }
}
