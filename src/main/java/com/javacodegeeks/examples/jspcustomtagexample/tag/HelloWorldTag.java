package com.javacodegeeks.examples.jspcustomtagexample.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by 유영모 on 2017-02-08.
 */
public class HelloWorldTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        final JspWriter out = getJspContext().getOut();
        out.println( "Hello JSP Custom Tag!" );
    }
}
