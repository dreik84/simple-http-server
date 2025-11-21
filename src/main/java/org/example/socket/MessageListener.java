package org.example.socket;

// наследуем наш класс от класса Thread
// для реализации многопоточности
// в нашем случае будет создаваться отдельный поток
// который будет постоянно слушать сообщения с сервера
public class MessageListener extends Thread {
    private boolean stop = false;

    private ClientSocket clientSocket;

    public MessageListener(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;

        // вызываем метод start, который получили от класса Thread
        // для создания нового потока
        // после его вызова будет выполняться логика метода run описанного ниже
        this.start();
    }

    @Override
    public void run() {
        System.out.println("[INFO] Готов принимать сообщения");

        String message = "";

        while (!this.stop) {
            // ожидаем сообщение от сервера
            message = this.clientSocket.getMessage();

            // при неожиданном закрытии серверной части чата
            // например, при нажатии сочетаний клавиш CTRL+C
            // клиент получит null, который мы обрабатываем
            if (message == null) {
                System.out.println("[ERROR] Ошибка сервера, закройте чат и повторите попытку позже");

                break;
            }

            System.out.print("\n" + message + "\n| ");
        }

        // после завершения работы метода run
        // поток умирает
    }

    public void stopListener() {
        this.stop = true;
    }
}
