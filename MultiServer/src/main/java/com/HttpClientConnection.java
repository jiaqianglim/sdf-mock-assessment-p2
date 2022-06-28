package com;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpClientConnection implements Runnable{
    private Socket s;
    InputStream is;
    OutputStream os;
    BufferedInputStream bis;
    BufferedOutputStream bos;
    byte[] buffer = new byte[1024];
    
    public HttpClientConnection(Socket s){
        this.s=s;
    }

    @Override
    public void run(){
        System.out.println("thread run started");
        try(InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();){
            while(true){
                InputStreamReader isr = new InputStreamReader(is);
                BufferedOutputStream bos = new BufferedOutputStream(os);
                System.out.println("While loop started");
                BufferedReader br = new BufferedReader(isr);
                String request = br.readLine();
                System.out.println(request);
                String[] requestAr = request.split(" ");
                if(requestAr[0].equals("GET")
                &&requestAr[1].equals("/index.html")){
                    String resource = "/index.html";
                    Path indexpath = Paths.get(HttpServer.root, HttpServer.docRoot, resource);
                    File indexfile = indexpath.toFile();
                    InputStream iis = new FileInputStream(indexfile);
                    byte[] buffer = new byte[1024];
                    int size;
                    bos.write("HTTP/1.1 200 OK\r\n\r\n".getBytes("utf-8"));
                    while (-1 != (size = iis.read(buffer)))
                        bos.write(buffer, 0 ,size);
                    iis.close();
                    bos.flush();
                    break;
                }
            }
        bos.close();
        os.close();
        bis.close();
        is.close();
        }catch(EOFException e){
            System.out.println("Server connection ended");
        }catch(FileNotFoundException e){
            System.out.println("File missing");
        }catch(IOException e){
            System.out.println("IOE");
        }
    }
}
