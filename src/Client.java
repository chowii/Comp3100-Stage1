import data.DsSystem;
import data.Job;
import data.Server;
import scheduler.BestFitServerProvider;
import scheduler.FirstFitServerProvider;
import scheduler.LargestServerProvider;
import scheduler.ServerProvider;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.xml.bind.JAXBException;

public class Client {
    private ClientRepository mRepository;
    private String message;
    Server largestServer = null;
    ServerProvider mServerProvider = null;

    /**
     * [main]
     * The main function which calls and runs all the other methods
     * resulting in the scheduling of jobs via the specification
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("========================================STARTED========================================");
        DsSystem dsSystem = null;
        ClientRepository repository = new ClientRepository();
        FirstFitServerProvider firstFit = new FirstFitServerProvider();
//        BestFitServerProvider bestFit = new BestFitServerProvider();
        Client client = new Client(repository, firstFit);
        client.connectToServer();
        client.serverHandshake();

        try {
            Path absolutePath = FileSystems.getDefault().getPath("").toAbsolutePath();
            dsSystem = ParseXml.parse(absolutePath + "/ds-system.xml", DsSystem.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        dsSystem.getServerArray().getServerList().sort(Comparator.comparingInt(Server::getCoreCount));
        dsSystem.getServerArray().getServerList().forEach(server -> {
            System.out.println(
                    "Type: " + server.getType() + "\n" +
                            " Limit: " + server.getLimit() + "\n" +
                            " ID: " + server.getId() + "\n" +
                            " Core: " + server.getCoreCount() + "\n" +
                            " Memory: " + server.getMemory() + "\n" +
                            " Disk: " + server.getDisk() + "\n" +
                            " Runtime: " + server.getEstimatedRuntime()
            );
        });
        firstFit.setServerArrayList(dsSystem.getServerArray().getServerList());
//        bestFit.setServerArrayList(dsSystem.getServerArray().getServerList());
        client.largestServer = dsSystem.getServerArray().getServerList().get(0);
        client.scheduleJobs();
        System.out.println("========================================COMPLETED========================================");
    }

    public Client(ClientRepository repository, ServerProvider serverProvider) {
        mRepository = repository;
        mServerProvider = serverProvider;
    }

    // Calling the connectToServer method from the ClientRepository
    public void connectToServer() {
        try {
            mRepository.connectToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * [serverHandshake description]
     * Initialising the connection between the client and the server
     * and getting the Auth along with the device name to print
     */
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

    /**
     * [scheduleJobs]
     * This is the main job scheduling part after handshaking with the server
     * The Client and server will continute to send and receive messages.
     * The Client will continue to listen so long as it doesn't get a "NONE"
     * from the server, which signifies that there are not jobs left.
     *
     * in our code below we have implemented a switch case for all the different
     * types of senarious or messages the server can send the client so that it
     * can be individually dealt with.
     */
    private void scheduleJobs() {
        try {
            // Sends the first "REDY" to obtain the first job
            mRepository.sendMessage("REDY");
            String[] messageArray = null;

            // If a "NONE" is not recieved from the server it means that there are more jobs to schedule
            while (!mRepository.isNoneReceived) {

                // After the message has been read, we then split it into mutiple parts by placing it in an array
                message = mRepository.readMessage();
                messageArray = message.split(" ");

                switch (messageArray[0]) {
                    case "JOBN": // same as "JOBP"
                    case "JOBP":
                        Job job = new Job(messageArray);
                        mRepository.sendMessage("GETS Capable " + job.GET());
                        message = mRepository.readMessage();
                        int numOfLines = Integer.parseInt(message.split(" ")[1]);
                        mRepository.sendMessage("OK");
                        ArrayList<Server> serverList = mRepository.getServerList(numOfLines);
                        serverList.sort(Comparator.comparingInt(a -> a.getServerState().ordinal()));
                        long idleCount = serverList.stream().filter(server -> server.getServerState() == Server.ServerState.IDLE).count();
                        long activeCount = serverList.stream().filter(server -> server.getServerState() == Server.ServerState.ACTIVE).count();
                        long inactiveCount = serverList.stream().filter(server -> server.getServerState() == Server.ServerState.INACTIVE).count();
                        long bootingCount = serverList.stream().filter(server -> server.getServerState() == Server.ServerState.BOOTING).count();
                        long unavailCount = serverList.stream().filter(server -> server.getServerState() == Server.ServerState.UNAVAILABLE).count();

//                        System.out.println(
//                                "idle: " + idleCount + "\n" +
//                                "active: " + activeCount + "\n" +
//                                "inactive: " + inactiveCount + "\n" +
//                                "booting: " + bootingCount + "\n" +
//                                "unavail: " + unavailCount + "\n"
//                        );

//                        serverList.forEach(server -> {
//                            System.out.println("======================================== "+ server.getType().toUpperCase() +" ========================================");
//                            System.out.println(
//                                    "Type: " + server.getType() + "\n" +
//                                    "Id: " + server.getId() + "\n" +
//                                    "State: " + server.getState() + "\n" +
//                                    "Core: " + server.getCoreCount() + "\n" +
//                                    "Memory: " + server.getMemory() + "\n" +
//                                    "Disk: " + server.getDisk() + "\n" +
//                                    "Runtime: " + server.getEstimatedRuntime()
//                            );
//                            System.out.println("-------------------------------------------------------------------------------");
//                        });

                        mRepository.sendMessage("OK");
                        message = mRepository.readMessage();
                        if (mServerProvider instanceof  FirstFitServerProvider)
                            ((FirstFitServerProvider) mServerProvider).setJob(job);
                        else if (mServerProvider instanceof BestFitServerProvider)
                            ((BestFitServerProvider) mServerProvider).setJob(job);
                        String serverDetails = mServerProvider.getServer(largestServer.getType(), serverList);
                        mRepository.sendMessage("SCHD " + job.getJobId() + " " + serverDetails);
                        break;
                    // When server sends a complete message we send a REDY to fetch another job
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
        // Sends a message to the server to "QUIT"
        mRepository.sendMessage("QUIT");
        message = mRepository.readMessage();
        // Server sends back response to "Quit"
        if (message.equals("QUIT")) {
            // Connection closes
            mRepository.close();
        }
    }
}
