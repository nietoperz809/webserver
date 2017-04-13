package com.webserver;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;

class SimpleWebserver
{
    private static byte[] makePage (String content)
    {
        String form = "<form action=\"\" method=\"GET\">\n" +
                "<input type=\"text\" name=\"msg\" value=\"\" size=\"30\" maxlength=\"50\">\n" +
                "</form>";
        String sb = "<html>" +
                "<head>\n<meta charset=\"utf-8\"/>" +
                "</head><body><hr>" +
                content +
                "<hr>" +
                form +
                "</body></html>";
        return sb.getBytes();
    }


    public static void main (String[] args) throws IOException
    {
        HttpServer server = HttpServer.create(new InetSocketAddress (80),1000);

        HttpHandler httpHandler = e ->
        {
            String[] txt = e.getRequestURI().toString().split("=");
            OutputStream os = e.getResponseBody();
            if (txt.length == 2)
            {
                e.sendResponseHeaders(200, 0);
                os.write(makePage(URLDecoder.decode(txt[1], "UTF-8" )));
            }
            os.close();
        };

        server.createContext("/", httpHandler);
        server.start();
        System.out.println("Running");
    }
}
