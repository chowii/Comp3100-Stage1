package data;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class ServerArray {

    private List<Server> serverList;

    /**
     * returns the serverList where the XML
     * attribute name is "server".
     */
    @XmlElement(name = "server")
    public List<Server> getServerList() {
        return serverList;
    }

    public void setServerList(List<Server> serverList) {
        this.serverList = serverList;
    }
}
