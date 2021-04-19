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
        // Running on 127.0.0.1/localhost on the default port of 50_000
        this("localhost", 50_000);
    }

    public ClientRepository(String host, int port) {
        mHost = host; //"localhost"
        mPort = port; //"50_000"
    }

    public void connectToServer() throws IOException {
        socket = new Socket(mHost, mPort);
        // initalise inputReader to read messages
        inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // initalise outputStream to send messages
        outputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void sendMessage(String message) throws IOException {
        // Sends message from String form
        outputStream.write((message + "\n").getBytes());
        outputStream.flush();
    }

    public String readMessage() throws IOException {
        message = inputReader.readLine();
        // Reads message and checks if "NONE" is recieved if so, boolean is = true
        isNoneReceived = message.equals("NONE");
        return message;
    }

    /**
     * [readMultiLineFromServer]
     *
     * This method essentialy reads 'n' number of lines from the server
     * each message is than stored into a ArrayList of Strings
     * meanwhile if a single message recieved is equal to "NONE"
     * the boolean value will turn true, and will catch any IOExceptions if
     * necessary
     *
     * @param (int) numLines
     * @return ArrayList<String>
     */
    public ArrayList<String> readMultiLineFromServer(int numLines) {
        try {
            // stores 'n' number of lines read into an Arraylist
            ArrayList<String> lines = new ArrayList<String>();
            // Loop which reads the messages and stores it into an ArrayList
            for (int i = 0; i < numLines; i++) {
                message = readMessage();
                lines.add(message);
                // boolean to check if "NONE" has been read
                isNoneReceived = message.equals("NONE");
            }
            return lines;
        } catch (IOException e) {
            message = "Error";
            return null;
        }
    }

    public void close() throws IOException {
        // Closes all the streams and connections
        inputReader.close();
        outputStream.close();
        socket.close();
    }
}
