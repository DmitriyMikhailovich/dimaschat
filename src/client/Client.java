package client;

import server.Connection;
import server.ConsoleHelper;
import server.Message;
import server.MessageType;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private String serverAddress;
    private int portServer;
    private Socket socket;
    private String name;
    protected static boolean connected = false;

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
        client.disconnect();
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
            ConsoleHelper.writeMessage("Соединение с сервером установлено");
            ClientReceiveThread clientReceiveThread = new ClientReceiveThread(connection, this);
            clientReceiveThread.start();
            try {
                while (!connected) {
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                ConsoleHelper.writeMessage("Ошибка соединения");
                return;
            }
            while (connected) {
                String message = ConsoleHelper.readMessage();
                if (message.equals("exit")) {
                    clientReceiveThread.interrupt();
                    connected = false;
                } else {
                    connection.sendMessage(new Message(name + ": " + message, MessageType.TEXT));
                }
            }

        } catch (IOException e) {
            ConsoleHelper.writeMessage("Соединение не установлено");
        } catch (Exception e) {
            ConsoleHelper.writeMessage("Неизвестная ошибка");
            e.printStackTrace();
        }
    }


    public void setName(String name) {
        this.name = name;
    }

    private void disconnect() {
        ConsoleHelper.writeMessage("Соединение с сервером разорвано");
    }
}
