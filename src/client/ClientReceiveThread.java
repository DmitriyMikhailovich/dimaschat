package client;

import server.Connection;
import server.ConsoleHelper;
import server.Message;
import server.MessageType;

import java.io.IOException;

public class ClientReceiveThread extends Thread {
    private Connection connection;
    private Client client;
    private String name;


    ClientReceiveThread(Connection connection, Client client) {
        this.connection = connection;
        this.client = client;
        setDaemon(true);
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message message = connection.receiveMessage();
                if (message.getMessageType() == MessageType.TEXT) {
                    ConsoleHelper.writeMessage(message.getMessage());
                }
                if (message.getMessageType() == MessageType.USER_REGISTRATION) {
                    ConsoleHelper.writeMessage(message.getMessage());
                    registrationClientToServer(connection);
                }
                if (message.getMessageType() == MessageType.USER_REGISTRATION_SUCCESSFUL) {
                    client.connected = true;
                    client.setName(name);
                    ConsoleHelper.writeMessage(message.getMessage());
                }
            } catch (IOException e) {

            } catch (ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Ошибка соединения, ClassNotFoundException");
            } catch (Exception e) {
                ConsoleHelper.writeMessage("Неизвестная ошибка соединения");
            }
        }
    }

    protected void registrationClientToServer(Connection connection) throws IOException {
        name = ConsoleHelper.readMessage();
        connection.sendMessage(new Message(name, MessageType.USER_REGISTRATION));
    }


}
