package scheduler;

import java.util.ArrayList;

/**
 * This class implements the ServerProvider interface implementing the logic
 * for providing Largest Server
 */
public class LargestServerProvider implements ServerProvider {
    /**
     * This method essentially obtain the largest server type
     * this is needed when we are doing the job scheduling since we
     * are only sending the jobs to the largest server in stage1
     *
     * Loops through the list of server to find the largest server
     *
     * @param serverList
     * @param serverType
     * @return serverType and id
     */
    @Override
    public String getServer(ArrayList<String> serverList, String serverType) {
        int id = 0;
        for(String server : serverList) {
            String[] receivedServerData = server.split(" ");
            String receivedServerType = receivedServerData[0];
            int receivedServerId = Integer.parseInt(receivedServerData[1]);
            if (receivedServerType.equals(serverType)) {
                id = Math.min(receivedServerId, id);
            }
        }
        return serverType + " " + id;
    }
}
