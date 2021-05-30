package scheduler;


import data.Server;

import java.util.*;

public class LowestWaitingJobsServerProvider implements ServerProvider {

    @Override
    public Server getServer(ArrayList<Server> serverList) {
        return Collections.min(serverList, Comparator.comparingInt(Server::getWaitingJobs));
    }
}
