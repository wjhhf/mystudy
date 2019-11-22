package com.hhf.study.util.study.mytomcat.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>Description: </p>
 *
 * @author hehuifeng
 * @Date 2019/11/139:30
 */
public class Server {

    private static ServerSocket serverSocket;
    private static int port=8080;
    private final static int POOL_SIZE=8;
    private static ExecutorService executorService;

    public static void start(){
        try {
            serverSocket=new ServerSocket(port);
            Socket socket = null;
            System.out.println("start server,port:"+port);
            executorService= Executors.newFixedThreadPool(POOL_SIZE);
            while(true){
                socket=serverSocket.accept();
                executorService.execute(new Handler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        start();
    }
}
