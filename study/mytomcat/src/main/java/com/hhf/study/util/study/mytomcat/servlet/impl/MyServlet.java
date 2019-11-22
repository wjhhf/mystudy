package com.hhf.study.util.study.mytomcat.servlet.impl;

import com.hhf.study.util.study.mytomcat.server.Request;
import com.hhf.study.util.study.mytomcat.server.Response;
import com.hhf.study.util.study.mytomcat.servlet.HttpServlet;

import java.io.IOException;

/**
 * <p>Description: </p>
 *
 * @author hehuifeng
 * @Date 2019/11/1310:23
 */
public class MyServlet extends HttpServlet {

    @Override
    public void doGet(Request request, Response response) throws IOException {
        response.write("<h1>my servlet hello</h1>");
    }
}
