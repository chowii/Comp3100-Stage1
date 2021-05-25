package scheduler;

import data.Server;

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
    public String getServer(String serverType, ArrayList<Server> serverList) {
        int id = 0;
        for(Server server : serverList) {
            String receivedServerType = server.getType();
            int receivedServerId = server.getId();
            if (receivedServerType.equals(serverType)) {
                id = Math.min(receivedServerId, id);
            }
        }
        return serverType + " " + id;
    }
}
