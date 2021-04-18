package data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "config")
public class Config {

    private ServerArray mServerArray;
    private String mRandomSeed;

    /**
     * Obtains the random seed stored in the ds-sample
     * Configuration files.
     */
    @XmlAttribute(name = "randomSeed")
    public String getRandomSeed() {
        return mRandomSeed;
    }

    public void setRandomSeed(String mRandomSeed) {
        this.mRandomSeed = mRandomSeed;
    }

    /**
     * returns the serverArray which was obtained from the
     * Configuration files.
     */
    @XmlElement(name = "servers")
    public ServerArray getServerArray() {
        return mServerArray;
    }

    public void setServerArray(ServerArray mServerArray) {
        this.mServerArray = mServerArray;
    }
}
