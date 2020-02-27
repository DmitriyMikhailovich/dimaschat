package server;

import java.io.IOException;
import java.net.Socket;

public class ServerClientThread extends Thread {
    private Socket socket;
    private Connection connectionClient;

    ServerClientThread(Socket clientSocket) {
        this.socket = clientSocket;
        setDaemon(true);
    }

    @Override
    public void run() {
        ConsoleHelper.writeMessage("Установлено соединение с новым пользователем");

        try (Connection connection = new Connection(socket);) {
            connectionClient = connection;
            Server.registrationUser(connection);
            while(true) {
                Message message = connection.receiveMessage();
                if (message.getMessageType() == MessageType.TEXT) {
                    Server.sendBroadcastMessage(message);
                }
            }

        } catch (IOException e) {
            ConsoleHelper.writeMessage("Ошибка соединения");
        } catch (ClassNotFoundException e) {
            ConsoleHelper.writeMessage("Ошибка");
        } catch (Exception e) {
            ConsoleHelper.writeMessage("Неизвестная ошибка");
        }
        Server.disconnectUser(connectionClient);
    }
}
