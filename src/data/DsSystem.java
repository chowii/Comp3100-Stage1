package data;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "system")
public class DsSystem {

    private ServerArray serverArray;

    public void setServerArray(ServerArray serverArray) {
        this.serverArray = serverArray;
    }

    /**
     * returns the serverArray which was obtained from the file
     * DsSystem.XML
     */
    @XmlElement(name = "servers")
    public ServerArray getServerArray() {
        return serverArray;
    }
}
