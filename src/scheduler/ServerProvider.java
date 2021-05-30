package scheduler;

import data.Server;

import java.util.ArrayList;

/**
 * In DS-Sim, the Client is responsible for choosing which Server Jobs
 * should be scheduled to.
 *
 * This interface abstracts the logic the Client will integrate when
 * scheduling Jobs to the Server.
 */
public interface ServerProvider {
    /**
     * Finds appropriate Server from serverList and serverType
     * @param serverList
     * @return Server type and id
     */
    Server getServer(ArrayList<Server> serverList);
}
