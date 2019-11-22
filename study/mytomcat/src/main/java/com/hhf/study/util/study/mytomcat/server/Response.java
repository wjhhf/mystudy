package com.hhf.study.util.study.mytomcat.server;

import java.io.PrintWriter;

/**
 * <p>Description: </p>
 *
 * @author hehuifeng
 * @Date 2019/11/139:47
 */
public class Response{

    private PrintWriter writer;

    public Response(PrintWriter writer) {
        this.writer = writer;
    }
    public void write(String msg) {
        writer.write(msg);
        writer.flush();
    }
}
