package data;

public class Job {
    String jobType;
    int submitTime;
    int jobId;
    int estRuntime;
    int core;
    int memory;
    int disk;

    /**
     * Parses all the XML of the job into this class
     * using a string array.
     *
     * @param jM
     */
    public Job(String[] jM) {
        this(
                jM[0],
                Integer.parseInt(jM[1]),
                Integer.parseInt(jM[2]),
                Integer.parseInt(jM[3]),
                Integer.parseInt(jM[4]),
                Integer.parseInt(jM[5]),
                Integer.parseInt(jM[6])
        );

    }

    /**
     * A parameterized version of parsing the data
     * into the job class, with 7 parameters.
     *
     * @param jobType
     * @param submitTime
     * @param jobId
     * @param estRuntime
     * @param core
     * @param memory
     * @param disk
     */
    public Job(String jobType, int submitTime, int jobId, int estRuntime, int core, int memory, int disk) {
        this.jobType = jobType; // JOBN or JOBP
        this.submitTime = submitTime;
        this.jobId = jobId;
        this.estRuntime = estRuntime;
        this.core = core;
        this.memory = memory;
        this.disk = disk;
    }

    public int getJobId() {
        return jobId;
    }

    public String GET() {
        return core + " " + memory + " " + disk;
    }
}
