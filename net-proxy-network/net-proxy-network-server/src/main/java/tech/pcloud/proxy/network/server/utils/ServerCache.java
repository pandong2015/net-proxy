package tech.pcloud.proxy.network.server.utils;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.configure.service.ClientSelector;
import tech.pcloud.proxy.network.core.NetworkModel;
import tech.pcloud.proxy.network.server.ProxyServer;
import tech.pcloud.proxy.network.server.model.ClientChannelPair;
import tech.pcloud.proxy.network.server.model.ServiceStatus;

import java.util.Map;

/**
 * @ClassName ServerCache
 * @Author pandong
 * @Date 2019/2/13 17:06
 **/
public enum ServerCache {
    INSTANCE;

    private Server server;
    private Map<Long, ClientChannelPair> clientChannelMapping = Maps.newConcurrentMap();
    private Map<Integer, ProxyServer> proxyServerMapping = Maps.newConcurrentMap();
    private Map<Long, Channel> proxyChannelMapping = Maps.newConcurrentMap();
    private Map<Integer, ClientSelector> servicePortClientsMapping = Maps.newConcurrentMap();
    private Map<Integer, Service> servicePortMapping = Maps.newConcurrentMap();

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void addProxyChannelMapping(long requestId, Service service, Client client, Channel channel, Channel clientChannel) {
        proxyChannelMapping.put(requestId, channel);
        channel.attr(NetworkModel.ChannelAttribute.REQUEST_ID).set(requestId);
        channel.attr(NetworkModel.ChannelAttribute.PROXY_SERVER_CHANNEL).set(clientChannel);
        channel.attr(NetworkModel.ChannelAttribute.SERVICE).set(service);
        channel.attr(NetworkModel.ChannelAttribute.CLIENT).set(client);
    }

    public void addClientChannelMapping(Client client, Channel channel) {
        clientChannelMapping.put(client.getId(), new ClientChannelPair(client, channel));
    }

    public void delService(Service service, Client client) {
        ClientSelector clientSelector = servicePortClientsMapping.get(service.getProxyPort());
        clientSelector.delClient(client);
        if (clientSelector.size() == 0) {
            ProxyServer proxyServer = proxyServerMapping.get(service.getProxyPort());
            proxyServer.shutdown();
            proxyServerMapping.remove(service.getProxyPort());
        }
    }

    public void delClient(Client client) {
        delClient(client.getId());
    }

    public void delClient(long clientId) {
        if (clientChannelMapping.containsKey(clientId)) {
            ClientChannelPair clientChannelPair = clientChannelMapping.get(clientId);
            clientChannelPair.getServices().values().forEach(s -> delService(s.getService(), clientChannelPair.getClient()));
        }
    }

    public void changeServiceProxyPortStatus(Service service, boolean openProxyPortStatus) {
        ClientSelector clientSelector = servicePortClientsMapping.get(service.getProxyPort());
        if (clientSelector == null) {
            return;
        }
        clientSelector.clients().forEach(c -> {
            setClientServiceProxyPortStatus(c.getId(), service, openProxyPortStatus);
        });
    }

    public void addClientServiceMapping(long clientId, Service service) {
        if (clientChannelMapping.containsKey(clientId)) {
            Client client = getClientWithId(clientId);
            servicePortMapping.put(service.getProxyPort(), service);
            ClientSelector clientSelector = servicePortClientsMapping.get(service.getProxyPort());
            if (clientSelector == null) {
                if (service.getClientSelector() == null) {
                    clientSelector = new DefaultClientSelector();
                } else {
                    try {
                        clientSelector = service.getClientSelector().newInstance();
                    } catch (Exception e) {
                        clientSelector = new DefaultClientSelector();
                    }
                }
                servicePortClientsMapping.put(service.getProxyPort(), clientSelector);
            }
            clientSelector.addClient(client);
        }
    }

    public Client getClientWithPort(int port) {
        return servicePortClientsMapping.get(port).next();
    }

    public Client getClientWithId(long clientId) {
        if (clientChannelMapping.containsKey(clientId)) {
            return clientChannelMapping.get(clientId).getClient();
        }
        return null;
    }

    public void setClientServiceProxyPortStatus(long clientId, Service service, boolean serviceProxyPortStatus) {
        ServiceStatus serviceStatus = getClientServiceStatus(clientId, service);
        if (serviceStatus != null) {
            serviceStatus.setOpenProxyPort(serviceProxyPortStatus);
        }
    }

    public ServiceStatus getClientServiceStatus(long clientId, Service service) {
        if (clientChannelMapping.containsKey(clientId)) {
            ClientChannelPair clientChannelPair = clientChannelMapping.get(clientId);
            return clientChannelPair.getService(service.getName());
        }
        return null;
    }

    public boolean checkClientServiceStatus(long clientId, Service service) {
        if (clientChannelMapping.containsKey(clientId)) {
            ClientChannelPair clientChannelPair = clientChannelMapping.get(clientId);
            return clientChannelPair.hasService(service.getName())
                    && clientChannelPair.getService(service.getName()).isOpenProxyPort();
        }
        return false;
    }

    public Channel getClientChannel(int port) {
        return getClientChannel(getClientWithPort(port));
    }

    public Channel getClientChannel(Client client) {
        if (client == null) {
            return null;
        }
        return getClientChannel(client);
    }

    public Channel getClientChannel(long clientId) {
        if (clientChannelMapping.containsKey(clientId)) {
            return clientChannelMapping.get(clientId).getChannel();
        }
        return null;
    }

    public Service getService(int port) {
        return servicePortMapping.get(port);
    }

    public void addProxyServerMapping(int port, ProxyServer proxyServer) {
        proxyServerMapping.put(port, proxyServer);
    }

    public ProxyServer getProxyServer(int port) {
        return proxyServerMapping.get(port);
    }
}
