package tech.pcloud.proxy.server.util;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.core.model.Node;
import tech.pcloud.proxy.core.model.Service;
import tech.pcloud.proxy.server.ProxyServer;

import java.util.Map;

@Slf4j
public class Global {
    private static final Map<Integer, ProxyServer> proxyServerMapping = Maps.newConcurrentMap();

    public interface ChannelAttributeName {
        String CLIENT = "client";
        String CLIENT_ID = "client_id";
        String PROXY_CHANNEL = "procy_channel";
        String REQUEST_CHANNEL = "request_channel";
        String REQUEST_ID = "request_id";
        String SERVICE = "service";
    }


    public interface ChannelAttribute {
        AttributeKey<Node> CLIENT = AttributeKey.newInstance(ChannelAttributeName.CLIENT);
        AttributeKey<Long> CLIENT_ID = AttributeKey.newInstance(ChannelAttributeName.CLIENT_ID);
        AttributeKey<Channel> PROXY_CHANNEL = AttributeKey.newInstance(ChannelAttributeName.PROXY_CHANNEL);
        AttributeKey<Channel> REQUEST_CHANNEL = AttributeKey.newInstance(ChannelAttributeName.REQUEST_CHANNEL);
        AttributeKey<Long> REQUEST_ID = AttributeKey.newInstance(ChannelAttributeName.REQUEST_ID);
        AttributeKey<Service> SERVICE = AttributeKey.newInstance(ChannelAttributeName.SERVICE);
    }

    public static void startProxyServer(Node node, Service service) {
        ProxyServer proxyServer = new ProxyServer(service, node);
        proxyServer.start();
        proxyServerMapping.put(service.getProxyPort(), proxyServer);
    }

    public static void stopProxyServer(Service service) {
        stopProxyServer(service.getProxyPort());
    }

    public static void stopProxyServer(int proxyPort) {
        ProxyServer proxyServer = proxyServerMapping.get(proxyPort);
        if (proxyServer == null) {
            log.warn("Proxy Server[" + proxyPort + "] not exist");
        }
        proxyServer.shutdown();
        proxyServerMapping.remove(proxyPort);
    }

    public static void restartProxyServer(Node node, Service service) {
        stopProxyServer(service);
        startProxyServer(node, service);
    }

    public static boolean checkProxyServer(int port){
        return proxyServerMapping.containsKey(port);
    }
}
