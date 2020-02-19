import java.io.*;
import java.net.Socket;

public class Connection {
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public Connection(Socket clientSocket) throws IOException {
        this.socket = clientSocket;
        this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
    }
}
