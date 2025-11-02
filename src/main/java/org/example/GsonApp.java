package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

public class GsonApp {
    public static void main(String[] args) {
        Owner owner = new Owner("John", "Smith", 1111);
        Dog dog = new Dog("Jack", 5, owner);
//        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder
                .setPrettyPrinting()
                .serializeNulls()
                .create();

        String jsonString = gson.toJson(dog);

        System.out.println(jsonString);

//        jsonString = "{\"name\":\"Tuzik\",\"age\":7,\"owner\":{\"firstName\":\"Tom\",\"lastName\":\"Hanks\",\"phoneNumber\":2222}}";
        jsonString = """
                {
                    "name": "Tuzik",
                    "age": 7,
                    "owner": {
                            "firstName": "Tom",
                            "lastName": "Hanks",
                            "phoneNumber": 2222
                        }
                }
                """;


        dog = gson.fromJson(jsonString, Dog.class);
        Map map = gson.fromJson(jsonString, Map.class);

        System.out.println(dog);
        System.out.println(map);
    }
}
