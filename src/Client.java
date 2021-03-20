import java.io.*;

public class Client {

    private ClientRepository mRepository;

    public static void main(String[] args) {
        System.out.println("Running");
        ClientRepository repository = new ClientRepository();
        Client client = new Client(repository);
        client.connectToServer();
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
}
