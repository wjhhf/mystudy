package com.hhf.study.util.study.mytomcat.servlet;

import com.hhf.study.util.study.mytomcat.server.Request;
import com.hhf.study.util.study.mytomcat.server.Response;

import java.io.IOException;

/**
 * <p>Description: </p>
 *
 * @author hehuifeng
 * @Date 2019/11/1310:22
 */
public abstract class HttpServlet {

    public void doGet(Request request, Response response)throws IOException {
        this.service(request, response);
    }
    public void doPost(Request request, Response response) throws IOException {
        this.service(request, response);
    }

    public void service(Request request, Response response) throws IOException {
        if("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        }else {
            doPost(request, response);
        }
    }
}
