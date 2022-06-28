package com;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer{
    public static int port = 3000;
    public static String docRoot = "/opt/tmp/www";
    public static String root = "/Users/ljq/Projects/Experiments/JAVAIO/MultiServer";

    ServerSocket ss;
    Socket s;

    public void start() throws IOException{
        
        ExecutorService threadPool = Executors.newFixedThreadPool(3) ;
        ServerSocket ss = new ServerSocket(port);

        while(true){
            Socket s = ss.accept();
            HttpClientConnection worker = new HttpClientConnection(s);
            threadPool.submit(worker);
            System.out.println("Thread submitted");
        }
    }
}