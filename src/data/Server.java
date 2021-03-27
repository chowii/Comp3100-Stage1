package data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "server")
public class Server {

    private String type;
    private String limit;
    private String bootupTime;
    private String hourlyRate;
    private String coreCount;
    private String memory;
    private String disk;

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

    public void setCoreCount(String coreCount) {
        this.coreCount = coreCount;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    @XmlAttribute(name = "type")
    public String getType() {
        return type;
    }

    @XmlAttribute(name = "limit")
    public String getLimit() {
        return limit;
    }

    @XmlAttribute(name = "bootupTime")
    public String getBootupTime() {
        return bootupTime;
    }

    @XmlAttribute(name = "hourlyRate")
    public String getHourlyRate() {
        return hourlyRate;
    }

    @XmlAttribute(name = "coreCount")
    public String getCoreCount() {
        return coreCount;
    }

    @XmlAttribute(name = "memory")
    public String getMemory() {
        return memory;
    }

    @XmlAttribute(name = "disk")
    public String getDisk() {
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
}
