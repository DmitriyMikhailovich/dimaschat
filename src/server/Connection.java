package server;

import java.io.*;
import java.net.Socket;

public class Connection implements Closeable {
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public Connection(Socket clientSocket) throws IOException {
        this.socket = clientSocket;
        this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
    }

    public void sendMessage(Message message) throws IOException {
        synchronized (objectOutputStream) {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        }
    }

    public Message receiveMessage() throws IOException, ClassNotFoundException {
        Message message = null;
        synchronized (objectInputStream) {
            message = (Message) objectInputStream.readObject();
            return message;
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
        objectInputStream.close();
        objectOutputStream.close();
    }
}
