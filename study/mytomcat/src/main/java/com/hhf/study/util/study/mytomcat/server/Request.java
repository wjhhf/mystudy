package com.hhf.study.util.study.mytomcat.server;

import com.hhf.study.util.study.mytomcat.servlet.HttpServlet;

import java.util.Map;

/**
 * <p>Description: </p>
 *
 * @author hehuifeng
 * @Date 2019/11/139:46
 */
public class Request{
    private String path;
    private String method;
    private String parameter;
    private Map<String, String> attribute;

    public Request() {
    }
    public HttpServlet initServlet(){
        return ServletContainer.getHttpServlet(path);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Map<String, String> getAttribute() {
        return attribute;
    }

    public void setAttribute(Map<String, String> attribute) {
        this.attribute = attribute;
    }
}
