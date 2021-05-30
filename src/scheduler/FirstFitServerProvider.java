package scheduler;


import data.Server;

import java.util.*;

public class FirstFitServerProvider implements ServerProvider {

    @Override
    public Server getServer(ArrayList<Server> serverList) {
        return Collections.min(serverList, Comparator.comparingInt(Server::getWaitingTime));
    }
}
