package org.example.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class MySocketClient {

    private static String domain;

    private static int port;

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void start() {
        boolean correctPort = false;
        boolean correctDomain = false;

        System.out.print("___Настройка клиента самого крутого чата___\n\n");

        while (!correctDomain) {
            System.out.print("Введите домен сервера: ");

            try {
                domain = reader.readLine();
            } catch (IOException e) {
                System.out.println("[ERROR] Че-то пошло не так, давай еще раз");
                continue;
            } catch (Exception e) {
                System.out.println("[ERROR] Ошибка");
                continue;
            }

            correctDomain = true;
        }

        while (!correctPort) {
            System.out.print("Введите порт: ");

            try {
                port = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                System.out.println("[ERROR] Че-то пошло не так, давай еще раз");
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
            ClientSocket clientSocket = new ClientSocket(new Socket(domain, port));
            String message = "";

            while (!message.equals("#exit")) {
                System.out.print("| ");
                message = reader.readLine();
                clientSocket.sendMessage(message);
            }

            System.out.println("[INFO] Закрытие чата...");

            clientSocket.closeClient();
        } catch (UnknownHostException e) {
            System.out.println("[ERROR] Неизвестный хост");
        } catch (IOException e) {
            System.out.println("[ERROR] Че-то пошло не так");
        } catch (Exception e) {
            System.out.println("[ERROR] Ошибка");
        }
    }
}
