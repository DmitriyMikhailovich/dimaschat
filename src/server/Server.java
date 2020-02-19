package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private  boolean running = true;
    protected ConcurrentHashMap<String, Connection> mapAllConnections = new ConcurrentHashMap<String, Connection>();

    public static void main(String[] args) {
        Server server = new Server();
        ConsoleHelper.writeMessage("Введите номер порта:");
        int portServer = ConsoleHelper.readInt();
        try (ServerSocket serverSocket = new ServerSocket(portServer)) {
            ConsoleHelper.writeMessage("Сервер запущен");//создаем сервер
            int i = 0;
            while (server.running) {
                Socket clientSocket = serverSocket.accept();    //ждем подключения клиента
                ServerClientThread serverClientThread = new ServerClientThread(clientSocket, server);   //передаём подключение в другой потом и ждем следующего клиента
                serverClientThread.start();
            }
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Неизвестная ошибка создания сервера");
        }
    }

    protected void sendBroadcastMessage (Message message) throws IOException {
        for (Connection connection: mapAllConnections.values()) {
            connection.sendMessage(message);
            ConsoleHelper.writeMessage(message.getMessage());
        }
    }


}
