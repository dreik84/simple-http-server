package org.example.socket;

import java.io.*;
import java.net.Socket;

public class ClientSocket {

    private Socket socket;

    private BufferedReader in;

    private BufferedWriter out;

    private MessageListener messageListener;

    public ClientSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.messageListener = new MessageListener(this);
    }

    public void sendMessage(String message) {
        try {
            this.out.write(message + "\n");
            this.out.flush();
        } catch (IOException e) {
            System.out.println("[ERROR] Ошибка при отправке сообщения");
        } catch (Exception e) {
            System.out.println("[ERROR] Ошибка");
        }
    }

    public String getMessage() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.out.println("[ERROR] Ошибка при получении сообщения");
        } catch (Exception e) {
            System.out.println("[ERROR] Ошибка");
        }

        return "";
    }

    public void closeClient() {
        try {
            socket.close();
            in.close();
            out.close();
            messageListener.stopListener();
        } catch (IOException e) {
            System.out.println("[ERROR] Ошибка");
        } catch (Exception e) {
            System.out.println("[ERROR] Ошибка");
        }
    }
}
