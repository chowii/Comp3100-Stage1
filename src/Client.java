import data.DsSystem;
import data.Job;
import data.Server;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBException;

public class Client {
    private ClientRepository mRepository;
    private String message;
    Server largestServer = null;

    public static void main(String[] args) {
        DsSystem dsSystem = null;

        try {
            dsSystem = ParseXml.parse("/home/chowii/IdeaProjects/Comp3100/src/ds-system.xml", DsSystem.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        ClientRepository repository = new ClientRepository();
        Client client = new Client(repository);
        client.connectToServer();
        client.serverHandshake();
        client.largestServer = Collections.max(dsSystem.getServerArray().getServerList());
        client.scheduleJobs();
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
            message = mRepository.readMessage();
            mRepository.sendMessage("AUTH " + System.getProperty("user.name"));
            message = mRepository.readMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void scheduleJobs() {

        try {
            mRepository.sendMessage("REDY");
            String[] messageArray = null;
            while (!mRepository.isNoneReceived) {
                message = mRepository.readMessage();
                messageArray = message.split(" ");
                 switch(messageArray[0]) {
                    case "JOBN":
                    case "JOBP":
                        Job job = new Job(messageArray);
                        mRepository.sendMessage("GETS Avail " + job.GET());
                        message = mRepository.readMessage();
                        int numOfLines = Integer.parseInt(message.split(" ")[1]);
                        mRepository.sendMessage("OK");
                        ArrayList<String> serverStatus = mRepository.readMultiLineFromServer(numOfLines);
                        mRepository.sendMessage("OK");
                        message = mRepository.readMessage();
                        mRepository.sendMessage("SCHD " + job.getJobId() + " " + getFirstLargestServer(serverStatus));
                        break;
                    case "JCPL":
                    case "OK":
                        mRepository.sendMessage("REDY");
                        break;
                     default:
                     break;
                }
            }

            quit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void quit() throws IOException {
        mRepository.sendMessage("QUIT");
        message = mRepository.readMessage();
        if (message.equals("QUIT")) {
            mRepository.close();
        }
    }

    public String getFirstLargestServer(ArrayList<String> serverStatusList) {
        String largestServerType = largestServer.getType();
        int id = 0;
        String[] statusValueArray;
        for(String status : serverStatusList) {
            statusValueArray = status.split(" ");
            if (statusValueArray[0].equals(largestServerType)) {
                id = Math.min(Integer.parseInt(statusValueArray[1]), id);
            }
        }
        return largestServerType + " " + id;
    }
}
