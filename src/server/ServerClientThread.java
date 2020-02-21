package server;

import java.io.IOException;
import java.net.Socket;

public class ServerClientThread extends Thread {
    private Socket socket;
    private Server server;
    private Connection connection;
     ServerClientThread(Socket clientSocket, Server server) {
        this.socket = clientSocket;
        this.server = server;
        setDaemon(true);
    }

    @Override
    public void run() {
        ConsoleHelper.writeMessage("Установлено соединение с сервером");

        try  {
            Connection connection = new Connection(socket);
            server.registrationUser(connection);
            while(true) {
                Message message = connection.receiveMessage();
                if (message.getMessageType() == MessageType.TEXT) {
                    server.sendBroadcastMessage(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
