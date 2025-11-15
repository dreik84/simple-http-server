package org.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int decision = 0;

        while (decision != 3) {
            System.out.print(
                    "___Menu___\n\n"
                            + "1. Client\n"
                            + "2. Server\n"
                            + "3. Exit\n"
                            + "| "
            );

            decision = reader.nextInt();

            switch (decision) {
                case 1:
                    MySocketClient.start();
                    break;
                case 2:
                    MySocketServer.start();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid argument");
            }
        }
    }
}
