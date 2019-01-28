package tech.pcloud.proxy.configure.xml.client.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName ClientConfigure
 * @Author pandong
 * @Date 2019/1/28 15:55
 **/
@XmlRootElement(name = "Configure")
public class ClientConfigure {
    private ServersInfo servers;
    private ClientInfo client;

    public ServersInfo getServers() {
        return servers;
    }

    @XmlElement(name = "Servers")
    public void setServers(ServersInfo servers) {
        this.servers = servers;
    }

    public ClientInfo getClient() {
        return client;
    }

    @XmlElement(name = "Client")
    public void setClient(ClientInfo client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "ClientConfigure{" +
                "servers=" + servers +
                ", client=" + client +
                '}';
    }

    public static void main(String[] args) {
        ClientConfigure clientConfigure = new ClientConfigure();
        ServerInfo server = new ServerInfo();
        server.setHose("localhost");
        server.setPort(8888);
        ServersInfo serversInfo = new ServersInfo();
        serversInfo.addServer(server);
        clientConfigure.setServers(serversInfo);

        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setPort(9999);
        ServicesInfo servicesInfo = new ServicesInfo();
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setName("test");
        serviceInfo.setProxyPoint(9999);
        serviceInfo.setStatus(1);
        ServiceHostsInfo serviceHostsInfo = new ServiceHostsInfo();
        ServiceHostInfo serviceHostInfo = new ServiceHostInfo();
        serviceHostInfo.setHost("123456");
        serviceHostInfo.setPort(2222);
        serviceHostsInfo.addHost(serviceHostInfo);
        serviceInfo.setHosts(serviceHostsInfo);
        servicesInfo.addService(serviceInfo);
        clientInfo.setServices(servicesInfo);
        clientConfigure.setClient(clientInfo);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ClientConfigure.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(clientConfigure, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
