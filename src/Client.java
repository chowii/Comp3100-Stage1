import java.io.*;

public class Client {

    private ClientRepository mRepository;

    public static void main(String[] args) {
        System.out.println("Running");
        ClientRepository repository = new ClientRepository();
        Client client = new Client(repository);
        client.connectToServer();
        client.serverHandshake();
    }

    public Client(ClientRepository repository) {
        mRepository = repository;
    }

    public void connectToServer() {
        try {
            mRepository.connectToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serverHandshake() {
        try {
            mRepository.sendMessage("HELO");
            mRepository.sendMessage("AUTH " + System.getProperty("user.name"));
            mRepository.sendMessage("REDY");
            String message = mRepository.readMessage();
            System.out.println("message: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scheduleJobs() {
        boolean end = false;
        try {
            String message = mRepository.readMessage();
            if (!message.equals("NONE")) {
                while (!end) {
                    if (message.equals("OK")) {
                        mRepository.sendMessage("REDY");
                        message = mRepository.readMessage();
                    }
                    if (message.equals("NONE")) {
                        end = true;
                        break;
                    }
                    String count = message.split("\\s+")[2];
                    System.out.println("count: " + count);
                    // Todo investigate how to schedule jobs
                    // mRepository.sendMessage("SCHD 1 joon 0" );
                    message = mRepository.readMessage();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
