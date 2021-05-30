package data;

public class Server implements Comparable<Server> {

    private final String type;
    private final int id;
    private final String state;
    private final int estimatedRuntime;
    private final int coreCount;
    private final int memory;
    private final int disk;
    private final int waitingJobs;
    private final int runningJobs;

    public Server(String type, int id, String state, int estimatedRuntime, int coreCount, int memory, int disk, int waitingJobs, int runningJobs) {
        this.type = type;
        this.id = id;
        this.state = state;
        this.estimatedRuntime = estimatedRuntime;
        this.coreCount = coreCount;
        this.memory = memory;
        this.disk = disk;
        this.waitingJobs = waitingJobs;
        this.runningJobs = runningJobs;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public int getWaitingJobs() {
        return waitingJobs;
    }

    @Override
    public String toString() {
        return "type: " + type +
                " id: " +
                id +
                " state: " +
                state +
                " coreCount: " +
                coreCount +
                " memory: " +
                memory +
                " disk: " +
                disk +
                " estimatedRuntime: " +
                estimatedRuntime +
                " waitingJobs: " +
                waitingJobs +
                " estimatedRuntime: " +
                estimatedRuntime;
    }

    @Override
    public int compareTo(Server server) {
        // The order of comparison is : Corecount
        return Integer.compare(server.getWaitingJobs(), waitingJobs);
    }
}
