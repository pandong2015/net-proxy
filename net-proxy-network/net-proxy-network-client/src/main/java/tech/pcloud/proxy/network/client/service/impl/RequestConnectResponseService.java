package tech.pcloud.proxy.network.client.service.impl;

import com.alibaba.fastjson.TypeReference;
import io.netty.channel.Channel;
import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.core.Result;
import tech.pcloud.proxy.core.service.IdGenerateService;
import tech.pcloud.proxy.network.client.ProxyClient;
import tech.pcloud.proxy.network.client.model.ClientInfo;
import tech.pcloud.proxy.network.client.utils.ClientCache;
import tech.pcloud.proxy.network.core.NetworkModel;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.core.service.CommandService;
import tech.pcloud.proxy.network.core.service.GetContentObject;
import tech.pcloud.proxy.network.core.service.adaptors.*;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.util.Map;


/**
 * @ClassName RegisterResponseCommandService
 * @Author pandong
 * @Date 2019/1/30 14:25
 * 处理访问请求
 **/
public class RequestConnectResponseService
        implements CommandService<Service>, GetServiceNodeType, GetRequestType, GetRequestOperation, GetObjectContentObject<Service> {
    @Override
    public void execCommand(ProtocolPackage.Operation operation, ProtocolCommand command, Channel channel, Service content) throws Exception {
        InetSocketAddress local = (InetSocketAddress) channel.localAddress();
        ClientInfo clientInfo = ClientCache.getClientInfoWithPort(local.getPort());
        Map<String, String> header = channel.attr(NetworkModel.ChannelAttribute.HEADER).get();
        Long requestId = null;
        if (header.containsKey(NetworkModel.ChannelAttributeName.REQUEST_ID)) {
            requestId = Long.parseLong(header.get(NetworkModel.ChannelAttributeName.REQUEST_ID));
        } else {
            requestId = IdGenerateService.generate(IdGenerateService.IdType.REQUEST);
        }
        getLogger().info("begin connect service...");
        ProxyClient proxyClient = new ProxyClient(clientInfo.getServiceWithProxyPort(content.getProxyPort()), clientInfo);
        proxyClient.connect(requestId);
    }

    @Override
    public Type getContentType() {
        return new TypeReference<Service>() {
        }.getType();
    }
}
