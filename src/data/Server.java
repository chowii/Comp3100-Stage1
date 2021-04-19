package data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "server")
public class Server implements Comparable<Server> {

    private String type;
    private String limit;
    private String bootupTime;
    private String hourlyRate;
    private int coreCount;
    private int memory;
    private int disk;

    // The Setters for the server
    public void setType(String type) {
        this.type = type;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public void setBootupTime(String bootupTime) {
        this.bootupTime = bootupTime;
    }

    public void setHourlyRate(String hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void setCoreCount(int coreCount) {
        this.coreCount = coreCount;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }

    /**
     * This method returns the server type,
     * the XML parser uses the annotation to
     * get the TYPE parsed from the XML attribute
     */
    @XmlAttribute(name = "type")
    public String getType() {
        return type;
    }

    /**
     * This method returns the limit of the server,
     * the XML parser uses the annotation to get
     * the limit which is parsed from thee XML attribute
     */
    @XmlAttribute(name = "limit")
    public String getLimit() {
        return limit;
    }

    /**
     * This method return the server bootupTime,
     * the XML parser uses the annotation to
     * get the bootuptime of the server
     */
    @XmlAttribute(name = "bootupTime")
    public String getBootupTime() {
        return bootupTime;
    }

    /**
     * This method is used to aquire the hourlyRate of the server
     * the XML parser uses the annotation to get the
     *  hourly rate of the server parsed from the XML attribute
     */
    @XmlAttribute(name = "hourlyRate")
    public String getHourlyRate() {
        return hourlyRate;
    }

    /**
     * This method is used to get the corecount of the servers,
     * the XML parser uses the annotation to get the coreCount
     * of the server which was parsed from the XML attribute
     */
    @XmlAttribute(name = "coreCount")
    public int getCoreCount() {
        return coreCount;
    }

    /**
     * This method is used to attain the memory of the server,
     * the XML parser uses the annotation to get the memory
     * of the server which was parsed from the XML attribute
     */
    @XmlAttribute(name = "memory")
    public int getMemory() {
        return memory;
    }

    /**
     * This metod is used to get the disk size of the server,
     * the XML parser uses the annotation to get the memory
     * of the server which was parsed from the XML attribute
     */
    @XmlAttribute(name = "disk")
    public int getDisk() {
        return disk;
    }

    @Override
    public String toString() {
        return "type: " + type +
                " limit: " +
                limit +
                " bootupTime: " +
                bootupTime +
                " hourlyRate: " +
                hourlyRate +
                " coreCount: " +
                coreCount +
                " memory: " +
                memory +
                " disk: " +
                disk;
    }

    @Override
    public int compareTo(Server server) {
        // The order of comparison is : Corecount -> Memory -> Disk space
        int coreComparison = Integer.compare(coreCount, server.coreCount);
        int memoryComparison = Integer.compare(memory, server.memory);
        int diskComparison = Integer.compare(disk, server.disk);
        return coreComparison != 0 ? coreComparison : memoryComparison != 0 ? memoryComparison : diskComparison;
    }
}
