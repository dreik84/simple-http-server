package org.example.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

// наследуем наш класс от класса Thread
// для реализации многопоточности
// при создании экземляра этого класса будет создаваться отдельный поток
// который будет постоянно слушать сообщения от этого пользователя
public class User extends Thread {

    private String name;

    private Socket socket;

    private BufferedReader in;

    private BufferedWriter out;

    public User(Socket socket) throws IOException {
        this.socket = socket;

        // получаем input stream нашего сокета
        // для получения данных от пользователя
        this.in = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()
                )
        );

        // получаем output stream нашего сокета
        // для отправки данных пользователю
        this.out = new BufferedWriter(
                new OutputStreamWriter(
                        socket.getOutputStream()
                )
        );

        // вызываем метод start, который получили от класса Thread
        // для создания нового потока
        // после его вызова будет выполняться логика метода run описанного ниже
        this.start();
    }

    @Override
    public void run() {
        System.out.println(
                "[INFO] Новый поток для пользователя с IP: " + this.socket.getInetAddress()
        );

        String message = "";

        try {
            // отправляем пользователю сообщение с просьбой ввести свое имя
            this.out.write("[AUTH] Введите ваше имя: \n");

            this.out.flush();

            // ожидаем от пользователя его имя
            this.name = this.in.readLine();

            // уведомляем всех пользователей о подключении нового пользователя
            for (User user : MySocketServer.users)
                user.send("[INFO] " + this.name + " подключился");

        } catch (IOException e) {
            System.out.println("[ERROR] Ошибка");

            message = "#exit";
        } catch (Exception e) {
            System.out.println("[ERROR] Ошибка");

            message = "#exit";
        }

        while (!message.equals("#exit") || !this.name.equals("#exit")) {
            try {
                // ожидаем сообщение от клиентской части
                message = this.in.readLine();

                // если от клиента пришло сообщение '#exit'
                // то отправляем ему сообщение о закрытии соединения
                // затем выпадаем из цикла и поток умирает
                if(message.equals("#exit")) {
                    this.send("[INFO] Соединение закрывается");

                    break;
                }

                // отправляем всем клиентам сообщение, которое прислал пользователь
                // который обслуживается этим потоком
                for (User user : MySocketServer.users)
                    user.send(this.name + ": " + message);
            } catch (IOException e) {
                System.out.println("[ERROR] Ошибка при чтении данных");

                continue;
            } catch (Exception e) {
                System.out.println("[ERROR] Ошибка");

                continue;
            }
        }

        System.out.println(
                "[INFO] Поток пользователя с IP: " + this.socket.getInetAddress() + " умирает"
        );
    }

    // метод, который занимается отправкой данных на клиент
    public void send(String message) {
        try {
            this.out.write(message + "\n");

            this.out.flush();
        } catch (IOException e) {
            System.out.println("[ERROR] Ошибка при отправки данных");
        } catch (Exception e) {
            System.out.println("[ERROR] Ошибка");
        }
    }
}
