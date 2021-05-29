package scheduler;

import data.Job;
import data.Server;

import java.util.ArrayList;
import java.util.List;

public class FirstFitServerProvider implements ServerProvider {

    private ArrayList<Server> mServerArrayList;
    private Job mJob;

    public FirstFitServerProvider() {
    }

    public FirstFitServerProvider(List<Server> mServerArrayList) {
        this.mServerArrayList = (ArrayList<Server>) mServerArrayList;
    }

    public void setJob(Job job) {
        this.mJob = job;
    }

    public void setServerArrayList(List<Server> serverArrayList) {
        this.mServerArrayList = (ArrayList<Server>) serverArrayList;

    }

    @Override
    public String getServer(String serverType, ArrayList<Server> serverList) {
        for (Server server : serverList) {
            if (server.getCoreCount() >= mJob.getCore() && server.getDisk() >= mJob.getDisk() && server.getMemory() >= mJob.getMemory()) {
                return server.getType() + " " + server.getId();
            }
        }
        for (Server server : mServerArrayList) {
            if (server.getCoreCount() >= mJob.getCore() && server.getDisk() >= mJob.getDisk() && server.getMemory() >= mJob.getMemory()) {
                server.setId(0);
                return server.getType() + " " + server.getId();
            }
        }
        System.out.println("not found");
        return null;
    }
}
