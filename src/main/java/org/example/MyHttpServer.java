package org.example;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String[] parts = exchange.getRequestURI().getPath().split("/");
        String name = (parts.length >= 3) ? parts[2] : "";
        String response = "Hey " + name + "! Glad to see you on our server.";
        String profession = (parts.length >= 4) ? parts[3] : "";
        Headers requestHeaders = exchange.getRequestHeaders();
        if (requestHeaders.containsKey("X-Wish-Good-Day")) {
            List<String> values = requestHeaders.get("X-Wish-Good-Day");
            System.out.println(values);

            if (values.contains("true")) {
                response = "Hello " + profession + " " + name + "! Have a good day!";
            }
        }

        System.out.println(exchange.getRequestMethod());
        System.out.println(exchange.getRequestHeaders().entrySet());
        System.out.println(exchange.getRequestURI().getPath());
        System.out.println(exchange.getRequestURI().getPort());
        System.out.println(exchange.getRequestURI().getQuery());
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), DEFAULT_CHARSET);
        System.out.println("Тело запроса: \n" + body);

        exchange.sendResponseHeaders(200, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
