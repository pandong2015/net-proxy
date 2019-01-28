package tech.pcloud.proxy.configure.xml.client.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName ClientServer
 * @Author pandong
 * @Date 2019/1/28 16:06
 **/
@XmlRootElement(name = "Server")
public class ServerInfo {
    private String hose;
    private int port;

    public String getHose() {
        return hose;
    }

    @XmlAttribute
    public void setHose(String hose) {
        this.hose = hose;
    }

    public int getPort() {
        return port;
    }

    @XmlAttribute
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "hose='" + hose + '\'' +
                ", port=" + port +
                '}';
    }
}
