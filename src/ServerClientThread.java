import java.net.Socket;

public class ServerClientThread extends Thread {
    private Socket socket;
    public ServerClientThread (Socket clientSocket) {
        this.socket = clientSocket;
    }

    @Override
    public void run() {
        super.run();
    }
}
