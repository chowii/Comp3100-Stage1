package data;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "system")
public class DsSystem {

    private ServerArray serverArray;

    public void setServerArray(ServerArray serverArray) {
        this.serverArray = serverArray;
    }

    @XmlElement(name = "servers")
    public ServerArray getServerArray() {
        return serverArray;
    }
}
