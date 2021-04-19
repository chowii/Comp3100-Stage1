package scheduler;

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
     * @param serverType
     * @return Server type and id
     */
    String getServer(ArrayList<String> serverList, String serverType);
}
