package scheduler;

import data.Job;
import data.Server;

import java.util.ArrayList;
import java.util.List;

public class BestFitServerProvider implements ServerProvider {
    private ArrayList<Server> mServerArrayList;
    private Job mJob;

    public BestFitServerProvider() {

    }

    public BestFitServerProvider(List<Server> mServerArrayList) {
        this.mServerArrayList = (ArrayList<Server>) mServerArrayList;
    }


    public void setServerArrayList(List<Server> serverArrayList) {
        this.mServerArrayList = (ArrayList<Server>) serverArrayList;
    }

    public void setJob(Job job) {
        this.mJob = job;
    }

    @Override
    public String getServer(String serverType, ArrayList<Server> serverList) {
        int bestFit = Integer.MAX_VALUE;
        int minAvail = Integer.MAX_VALUE;
        Server bestServer = null;
        boolean found = false;

        for (Server server : serverList) {
            if (server.getCoreCount() >= mJob.getCore() && server.getDisk() >= mJob.getDisk() && server.getMemory() >= mJob.getMemory()) {
                int fitValue = server.getCoreCount() - mJob.getCore();
                if ((fitValue < bestFit) || fitValue == bestFit && server.getEstimatedRuntime() < minAvail) {
                    bestFit = fitValue;
                    minAvail = server.getEstimatedRuntime();
                    switch (server.getType()) {
                        case "inactive":
                        case "booting":
                        case "idle":
                        case "active":
                        case "unavailable":
                        {
                            found = true;
                            bestServer = server;
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
        }
        if (found) {
            return bestServer.getType() + " " + bestServer.getId();
        } else {
            int bestFitAlt = Integer.MAX_VALUE;
            Server bestServerAlt = null;
            for (Server server : mServerArrayList) {
                int fitValueAlt = server.getCoreCount() - mJob.getCore();
                if (fitValueAlt >= 0 && fitValueAlt < bestFitAlt && server.getDisk() >= mJob.getDisk() && server.getMemory() >= mJob.getMemory()) {
                    bestFitAlt = fitValueAlt;
                    bestServerAlt = server;
                }
            }
            bestServerAlt.setId(0);
            return bestServerAlt.getType() + " " + bestServerAlt.getId();
        }


    }
}
