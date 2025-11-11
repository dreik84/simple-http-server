package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MyHttpClient {
    public static void main(String[] args) throws IOException, InterruptedException {
//        URI uri = URI.create("https://httpstatuses.com/418");
//        URI uri = URI.create("http://httpbin.org/status/404");
//        URI uri = URI.create("https://api.agify.io?name=Pixel");
        URI uri = URI.create("http://localhost:8080/hello");

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
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

            JsonElement jsonElement = JsonParser.parseString(response.body());

            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                System.out.println(jsonObject.get("name"));
                System.out.println(jsonObject.get("age"));

                if (jsonObject.get("owner").isJsonObject()) {
                    JsonObject owner = jsonObject.getAsJsonObject("owner");
                    System.out.println(owner.get("firstName"));
                    System.out.println(owner.get("lastName"));
                    System.out.println(owner.get("phoneNumber"));
                }
            } else {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
            }


            System.out.println("Ответ: " + response.body());

        } catch (IOException ioe) {
            System.out.println("Во время выполнения запроса возникла ошибка");
        } catch (IllegalArgumentException iae) {
            System.out.println("Введённый вами адрес не соответствует формату URL");
        }
    }
}
