package tech.pcloud.proxy.network.server.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.configure.service.ClientSelector;
import tech.pcloud.proxy.network.core.NetworkModel;
import tech.pcloud.proxy.network.core.protocol.Operation;
import tech.pcloud.proxy.network.core.utils.ProtocolHelper;
import tech.pcloud.proxy.network.server.ProxyServer;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ServerCache
 * @Author pandong
 * @Date 2019/2/13 17:06
 **/
public enum ServerCache {
    INSTANCE;

    private Map<Client, Channel> clientChannelMapping = Maps.newConcurrentMap();
    private Map<Integer, ProxyServer> proxyServerMapping = Maps.newConcurrentMap();
    private Map<Long, Channel> proxyChannelMapping = Maps.newConcurrentMap();
    private Map<Integer, ClientSelector> servicePortClientsMapping = Maps.newConcurrentMap();
    private Map<Integer, Service> servicePortMapping = Maps.newConcurrentMap();

    public void addProxyChannelMapping(long requestId, Service service, Client client, Channel channel) {
        proxyChannelMapping.put(requestId, channel);
        Channel proxyChannel = getClientChannel(client);
        channel.attr(NetworkModel.ChannelAttribute.REQUEST_ID).set(requestId);
        channel.attr(NetworkModel.ChannelAttribute.PROXY_SERVER_CHANNEL).set(proxyChannel);
        channel.attr(NetworkModel.ChannelAttribute.SERVICE).set(service);
        channel.attr(NetworkModel.ChannelAttribute.CLIENT).set(client);
    }

    public void addClientChannelMapping(Client client, Channel channel) {
        clientChannelMapping.put(client, channel);
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
        clientChannelMapping.remove(client);
        client.getServices().forEach(s -> delService(s, client));
    }

    public void addClientServiceMapping(Service service, Client client) {
        if (clientChannelMapping.containsKey(client)) {
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

    public Client getClient(int port) {
        return servicePortClientsMapping.get(port).next();
    }

    public Channel getClientChannel(int port) {
        return getClientChannel(getClient(port));
    }

    public Channel getClientChannel(Client client) {
        if (client == null) {
            return null;
        }
        return clientChannelMapping.get(client);
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
