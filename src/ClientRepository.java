import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ClientRepository {

    private String mHost;
    private int mPort;
    private Socket socket;
    private BufferedReader inputReader;
    private DataOutputStream outputStream;
    String message = "";
    public boolean isNoneReceived = false;

    public ClientRepository() {
        this("localhost", 50_000);
    }

    public ClientRepository(String host, int port) {
        mHost = host;
        mPort = port;
    }
    
    public void connectToServer() throws IOException {
        socket = new Socket(mHost, mPort);
        inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void sendMessage(String message) throws IOException {
        outputStream.write((message + "\n").getBytes());
        outputStream.flush();
    }

    public void sendMessage(byte[] bytes) throws IOException {
        outputStream.write(bytes);
        outputStream.flush();
    }

    public String readMessage() throws IOException {
        message = inputReader.readLine();
        isNoneReceived = message.equals("NONE");
        return message;
    }

    public String getLastMessage() {
        return message;
    }

    public BufferedReader getInputReader() {
        return inputReader;
    }

    public ArrayList<String> readMultiLineFromServer(int numLines) {
        try {
            ArrayList<String> lines = new ArrayList<String>();
            for (int i = 0; i < numLines; i++) {
                message = readMessage();
                lines.add(message);
                isNoneReceived = message.equals("NONE");
            }
            return lines;
        } catch (IOException e) {
            message = "Error";
            return null;
        }
    }

    public void close() throws IOException {
        inputReader.close();
        outputStream.close();
        socket.close();
    }
}
