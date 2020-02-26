package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private  boolean running = true;
    protected static ConcurrentHashMap<Connection, String> mapAllConnections = new ConcurrentHashMap<Connection, String>();

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

    protected static void sendBroadcastMessage (Message message) throws IOException {
        for (Connection connection: mapAllConnections.keySet()) {
            connection.sendMessage(message);
            ConsoleHelper.writeMessage(message.getMessage());
        }
    }

    protected static void registrationUser (Connection connection) throws IOException, ClassNotFoundException {
        connection.sendMessage(new Message("Введите имя пользователя: ",  MessageType.USER_REGISTRATION));
        while (true) {
            Message message = connection.receiveMessage();
            String name;
            if ((name = message.getMessage()) != null && message.getMessageType() == MessageType.USER_REGISTRATION) {
                if (!mapAllConnections.containsValue(name)) {
                    mapAllConnections.put(connection, name);
                    connection.sendMessage(new Message("Регистрация пользователя произошла успешно", MessageType.USER_REGISTRATION_SUCCESSFUL));
                    return;
                } else {
                    connection.sendMessage(new Message("Данное имя занято, введите другое:", MessageType.USER_REGISTRATION));
                }
            }
        }
    }

    


}
