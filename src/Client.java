import data.DsSystem;
import data.Job;
import data.Server;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBException;

public class Client {
    private ClientRepository mRepository;
    private String message;
    Server largestServer = null;

    /**
     * [main]
     * The main fucnction which calls and runs all the other methods
     * resulting in the scheduling of jobs via the specification
     *
     * @param args
     */

    public static void main(String[] args) {
        DsSystem dsSystem = null;

        try {
            Path absolutePath = FileSystems.getDefault().getPath("").toAbsolutePath();
            dsSystem = ParseXml.parse(absolutePath + "/ds-system.xml", DsSystem.class);
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

    //Calling the connectToServer method from the ClientRepository
    public void connectToServer() {
        try {
            mRepository.connectToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  [serverHandshake description]
     *  Initialising the connection between the client and the server
     *  and getting the Auth along with the device name to print
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
            //Sends the first "REDY" to obatin the first job
            mRepository.sendMessage("REDY");
            String[] messageArray = null;

            //If a "NONE" is not recieved from the server it means that there are more jobs to schedule
            while (!mRepository.isNoneReceived) {

                //After the message has been read, we then split it into mutiple parts by placing it in an array
                message = mRepository.readMessage();
                messageArray = message.split(" ");

                 switch(messageArray[0]) {
                    case "JOBN": //same as "JOBP"
                    case "JOBP":
                        Job job = new Job(messageArray);
                        mRepository.sendMessage("GETS Avail " + job.GET());
                        message = mRepository.readMessage();
                        int numOfLines = Integer.parseInt(message.split(" ")[1]);
                        mRepository.sendMessage("OK");
                        ArrayList<String> serverStatus = mRepository.readMultiLineFromServer(numOfLines);
                        mRepository.sendMessage("OK");
                        message = mRepository.readMessage();
                        //Get the JOB and schedule it to the largestServer
                        mRepository.sendMessage("SCHD " + job.getJobId() + " " + getFirstLargestServer(serverStatus));
                        break;
                    //When server sends a complete message we send a REDY to fetch another job
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
        //Sends a message to the server to "QUIT"
        mRepository.sendMessage("QUIT");
        message = mRepository.readMessage();
        //Server sends back response to "Quit"
        if (message.equals("QUIT")) {
            //Connection closes
            mRepository.close();
        }
    }
    /**
    * [getFirstLargestServer]
    * This method essentialy obtain the largest server type
    * this is needed when we are doing the job scheduling since we
    * are only sending the jobs to the largest server in stage1
    *
    * @param ArrayList<String> serverStatusList
    * @return
    */
    public String getFirstLargestServer(ArrayList<String> serverStatusList) {
        String largestServerType = largestServer.getType();
        int id = 0;
        String[] statusValueArray;
        //Loops through the whole ArrayList to find the largestServerType
        for(String status : serverStatusList) {
            statusValueArray = status.split(" ");
            if (statusValueArray[0].equals(largestServerType)) {
                id = Math.min(Integer.parseInt(statusValueArray[1]), id);
            }
        }
        //returns a string of the largest servertype followed by the id
        return largestServerType + " " + id;
    }
}
