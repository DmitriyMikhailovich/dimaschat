package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static boolean running = true;
    private ConcurrentHashMap<String, Connection> mapAllConnections = new ConcurrentHashMap<String, Connection>();

    public static void main(String[] args) {
        ConsoleHelper.writeMessage("Введите номер порта:");
        int portServer = ConsoleHelper.readInt();
        try (ServerSocket serverSocket = new ServerSocket(portServer)) {
            ConsoleHelper.writeMessage("Сервер запущен");//создаем сервер
            while (running) {
                Socket clientSocket = serverSocket.accept();    //ждем подключения клиента
                ServerClientThread serverClientThread = new ServerClientThread(clientSocket);   //передаём подключение в другой потом и ждем следующего клиента
                serverClientThread.start();
                serverClientThread.setDaemon(true);
            }
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Неизвестная ошибка создания сервера");
        }
    }
}
