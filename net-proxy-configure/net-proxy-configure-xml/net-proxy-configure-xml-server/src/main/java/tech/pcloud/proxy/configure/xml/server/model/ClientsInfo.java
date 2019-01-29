package tech.pcloud.proxy.configure.xml.server.model;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @ClassName ClientsInfo
 * @Author pandong
 * @Date 2019/1/29 14:31
 **/
@XmlRootElement(name = "Clients")
public class ClientsInfo {
    private List<ClientInfo> clients = Lists.newArrayList();

    public List<ClientInfo> getClients() {
        return clients;
    }

    @XmlElement(name = "Client")
    public void setClients(List<ClientInfo> clients) {
        this.clients = clients;
    }
}
