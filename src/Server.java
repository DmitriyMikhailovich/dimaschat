import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static boolean running = true;

    public static void main(String[] args) {
        ConsoleHelper.writeMessage("Введите номер порта:");
        int portServer = ConsoleHelper.readInt();
        try (ServerSocket serverSocket = new ServerSocket(portServer)) {
            while (running) {
                Socket clientSocket = serverSocket.accept();
                ServerClientThread serverClientThread = new ServerClientThread(clientSocket);
            }
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Неизвестная ошибка создания сервера");
        }

    }
}
