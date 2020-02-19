package server;

import java.io.IOException;
import java.net.Socket;

public class ServerClientThread extends Thread {
    private Socket socket;
    public ServerClientThread (Socket clientSocket) {
        this.socket = clientSocket;
        setDaemon(true);
    }

    @Override
    public void run() {
        ConsoleHelper.writeMessage("Установлено соединение с сервером");

        try (Connection connection = new Connection(socket)) {
            while(true) {
                Message message = connection.receiveMessage();
                if (message.getMessageType() == MessageType.TEXT) {
                    ConsoleHelper.writeMessage(message.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
