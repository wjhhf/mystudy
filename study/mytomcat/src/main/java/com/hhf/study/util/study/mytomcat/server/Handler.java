package com.hhf.study.util.study.mytomcat.server;

import com.hhf.study.util.study.mytomcat.servlet.HttpServlet;

import java.io.*;
import java.net.Socket;

/**
 * <p>Description: </p>
 *
 * @author hehuifeng
 * @Date 2019/11/139:41
 */
public class Handler implements Runnable{

    private Socket socket;

    private PrintWriter writer;

    public Handler(Socket socket) {
        this.socket=socket;
    }

    @Override
    public void run() {
        try {
            writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: text/html;charset=utf-8");
            writer.println();

            Request request = new Request();
            Response response = new Response(writer);
            InputStream inputStream =socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while(true){
                String msg =reader.readLine();
                System.out.println("inputMsg:"+msg);
                if(msg==null||"".equals(msg.trim())){
                    break;
                }
                String[] msgs= msg.split(" ");
                if(msgs.length==3 && msgs[2].equalsIgnoreCase("HTTP/1.1")){
                    request.setMethod(msgs[0]);
                    request.setPath(msgs[1]);
                    break;
                }
            }
            if(request.getPath().endsWith("ico")) {
                return;
            }
            HttpServlet httpServlet = request.initServlet();
            dispatcher(httpServlet, request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
                socket.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void dispatcher(HttpServlet httpServlet,Request request,Response response){
        try{
            if(httpServlet==null){
                response.write("<h1>404 not found</h1>");
                return;
            }
            if("GET".equalsIgnoreCase(request.getMethod())){
                httpServlet.doGet(request, response);
            }else if("POST".equalsIgnoreCase(request.getMethod())){
                httpServlet.doPost(request, response);
            }
        }catch (Exception e){
            response.write("<h1>500 server error</h1>");
            e.printStackTrace();
        }finally {

        }

    }
}
