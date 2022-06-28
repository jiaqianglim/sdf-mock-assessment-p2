package com;

import java.io.IOException;

/**
 * @author lim jia qiang
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        HttpServer hs = new HttpServer();
        hs.start();
    }
}
