package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MyHttpClient {
    public static void main(String[] args) throws IOException, InterruptedException {
//        URI uri = URI.create("https://httpstatuses.com/418");
        URI uri = URI.create("http://httpbin.org/status/404");

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "text/html")
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        try {
            HttpResponse<String> response = client.send(request, handler);

            int status = response.statusCode();

            if (status >= 200 && status <= 299) {
                System.out.println("Сервер успешно обработал запрос. Код состояния: " + status);
            }

            if (status >= 400 && status <= 499) {
                System.out.println("Сервер сообщил о проблеме с запросом. Код состояния: " + status);
            }

        } catch (IOException ioe) {
            System.out.println("Во время выполнения запроса возникла ошибка");
        } catch (IllegalArgumentException iae) {
            System.out.println("Введённый вами адрес не соответствует формату URL");
        }
    }
}
