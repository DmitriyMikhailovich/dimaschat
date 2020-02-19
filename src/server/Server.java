package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static boolean running = true;

    public static void main(String[] args) {
        ConsoleHelper.writeMessage("Введите номер порта:");
        int portServer = ConsoleHelper.readInt();
        try (ServerSocket serverSocket = new ServerSocket(portServer)) {
            while (running) {
                Socket clientSocket = serverSocket.accept();    //ждем подключения клиента
                ServerClientThread serverClientThread = new ServerClientThread(clientSocket);      //передаём подключение в другой потом и ждем следующего клиента
            }
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Неизвестная ошибка создания сервера");
        }

    }
}
