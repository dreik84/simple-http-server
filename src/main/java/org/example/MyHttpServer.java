package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class MyHttpServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        int backlog = 0;

        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(port), backlog);
        httpServer.createContext("/hello", new MyHttpHandler());
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + port + " порту!");
    }
}

class MyHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Hey! Glad to see you on our server.";
        System.out.println(exchange.getRequestMethod());
        System.out.println(exchange.getRequestHeaders());
        System.out.println(exchange.getRequestURI());
        exchange.sendResponseHeaders(200, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
