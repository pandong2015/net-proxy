package tech.pcloud.proxy.configure.xml.client.model;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @ClassName ClientServer
 * @Author pandong
 * @Date 2019/1/28 16:06
 **/
@XmlRootElement(name = "Servers")
public class ServersInfo {
    private List<ServerInfo> servers = Lists.newArrayList();

    public void addServer(ServerInfo server) {
        servers.add(server);
    }

    public List<ServerInfo> getServers() {
        return servers;
    }

    @XmlElement(name = "Server")
    public void setServers(List<ServerInfo> servers) {
        this.servers = servers;
    }

    @Override
    public String toString() {
        return "ServersInfo{" +
                "servers=" + servers +
                '}';
    }
}
