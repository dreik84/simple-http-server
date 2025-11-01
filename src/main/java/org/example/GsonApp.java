package org.example;

import com.google.gson.Gson;

public class GsonApp {
    public static void main(String[] args) {
        Owner owner = new Owner("John", "Smith", 1111);
        Dog dog = new Dog("Jack", 5, owner);
        Gson gson = new Gson();

        String jsonString = gson.toJson(dog);

        System.out.println(jsonString);

        dog = gson.fromJson(jsonString, Dog.class);

        System.out.println(dog);
    }
}
