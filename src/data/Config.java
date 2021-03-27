package data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "config")
public class Config {

    private ServerArray mServerArray;
    private String mRandomSeed;

    @XmlAttribute(name = "randomSeed")
    public String getRandomSeed() {
        return mRandomSeed;
    }

    public void setRandomSeed(String mRandomSeed) {
        this.mRandomSeed = mRandomSeed;
    }

    @XmlElement(name = "servers")
    public ServerArray getServerArray() {
        return mServerArray;
    }

    public void setServerArray(ServerArray mServerArray) {
        this.mServerArray = mServerArray;
    }
}
