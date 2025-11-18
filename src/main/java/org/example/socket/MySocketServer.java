package org.example.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MySocketServer {

    private static int port;

    public static List<User> users = new ArrayList<>();

    private static ServerSocket serverSocket;

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void start() {
        boolean correctPort = false;

        System.out.print("___Настройка сервера самого крутого чата___\\n\\n");

        while (!correctPort) {
            System.out.print("Введите порт: ");

            try {
                port = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                System.out.println("[ERROR] Что-то пошло не так, давай ещё раз");
                continue;
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Будь добр, введи цифру");
                continue;
            } catch (Exception e) {
                System.out.println("[ERROR] Ошибка");
                continue;
            }

            correctPort = true;
        }

        try {
            serverSocket = new ServerSocket(port);

            System.out.println("___Стартовал сервер самого крутого чата___\n");

            while (true) {
                Socket newUser = serverSocket.accept();

                System.out.println(
                        "[INFO] Новое подключение\n"
                                + "[INFO] IP: " + newUser.getInetAddress()
                );
            }

        } catch (IOException e) {
            System.out.println("[ERROR] Произошла ошибка при старте сервера");
            return;
        } catch (Exception e) {
            System.out.println("[ERROR] Ошибка");
            return;
        }
    }
}
