package client;

import server.Connection;
import server.ConsoleHelper;
import server.Message;
import server.MessageType;

import java.io.IOException;

public class ClientReceiveThread extends Thread {
    private Connection connection;

    public ClientReceiveThread(Connection connection) {
        this.connection = connection;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = connection.receiveMessage();
                if (message.getMessageType() == MessageType.TEXT) {
                    ConsoleHelper.writeMessage(message.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
