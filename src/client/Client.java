package client;

import server.Connection;
import server.ConsoleHelper;
import server.Message;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private String serverAddress;
    private int portServer;
    private Socket socket;
    private String name;

    public static void main(String[] args) {
        Client client = new Client();
        boolean connectionLost = true;
        while (connectionLost) {
            try {
                client.startToConnection();
                connectionLost = false;
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Не правильно введен адресс или номер порта");
                ConsoleHelper.writeMessage("Повторите попытку");
            }
        }
        client.run();

    }

    private void startToConnection() throws IOException {
        ConsoleHelper.writeMessage("Введите адресс сервера");
        serverAddress = ConsoleHelper.readMessage();
        ConsoleHelper.writeMessage("Введите номер порта");
        portServer = ConsoleHelper.readInt();
        socket = new Socket(serverAddress, portServer);
    }

    private void run() {
        try (Connection connection = new Connection(socket)) {
            ConsoleHelper.writeMessage("Соединение установлено");
            while (true) {
                Message message = connection.receiveMessage();
                connection.sendMessage();
            }
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Соединение не установлено");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
