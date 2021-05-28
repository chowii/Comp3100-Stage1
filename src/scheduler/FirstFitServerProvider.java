package scheduler;

import data.Job;
import data.Server;

import java.util.ArrayList;
import java.util.List;

public class FirstFitServerProvider implements ServerProvider {

    private ArrayList<Server> mServerArrayList;
    private Job mJob;

    int startingIndexDsServerList = 0;
    int startingIndexJobServerList = 0;

    public FirstFitServerProvider() {
    }

    public FirstFitServerProvider(List<Server> mServerArrayList) {
        this.mServerArrayList = (ArrayList<Server>) mServerArrayList;
    }

    public void setJob(Job job) {
//        System.out.println("========================================JOB========================================");
//        System.out.println("ID: " + job.getJobId() + " " + job.GET());
        this.mJob = job;
    }

    public void setServerArrayList(List<Server> serverArrayList) {
        this.mServerArrayList = (ArrayList<Server>) serverArrayList;

    }

    @Override
    public String getServer(String serverType, ArrayList<Server> serverList) {
//        considerLogging();
//        for (int j = startingIndexDsServerList; j < mServerArrayList.size(); j++) {
//            Server dsServer = mServerArrayList.get(j);
            for (int i = startingIndexJobServerList; i <serverList.size(); i++) {
                Server server = serverList.get(i);
                // && server.getEstimatedRuntime() >= mJob.getEstRuntime()
//                if (dsServer.getType().equals(server.getType())) {
                    if (server.getCoreCount() >= mJob.getCore() && server.getDisk() >= mJob.getDisk() && server.getMemory() >= mJob.getMemory()) {
//                        startingIndexJobServerList = j;
                        startingIndexDsServerList = i;
                        System.out.println("found");
                        return server.getType() + " " + server.getId();
                    }
                }
//            }
//        }
        for (Server server : mServerArrayList) {
            if (server.getCoreCount() >= mJob.getCore() && server.getDisk() >= mJob.getDisk() && server.getMemory() >= mJob.getMemory()) {
                System.out.println("fallback");
                server.setId(0);
                return server.getType() + " " + server.getId();
            }
        }
        System.out.println("not found");
        return null;
    }

    private void considerLogging() {
        System.out.println("=================== ========================");
        System.out.println("ds: " + startingIndexDsServerList);
        System.out.println("js: " + startingIndexJobServerList);
    }
}
