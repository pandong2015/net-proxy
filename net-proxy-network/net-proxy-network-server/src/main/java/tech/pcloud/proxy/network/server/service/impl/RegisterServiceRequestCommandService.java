package tech.pcloud.proxy.network.server.service.impl;

import com.alibaba.fastjson.TypeReference;
import io.netty.channel.Channel;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.model.NodeType;
import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.network.core.NetworkModel;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.core.service.CommandService;
import tech.pcloud.proxy.network.core.service.adaptors.GetObjectContentObject;
import tech.pcloud.proxy.network.core.service.adaptors.GetNormalOperation;
import tech.pcloud.proxy.network.core.service.adaptors.GetRequestType;
import tech.pcloud.proxy.network.core.service.adaptors.GetServiceNodeType;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;
import tech.pcloud.proxy.network.server.ProxyServer;
import tech.pcloud.proxy.network.server.exceptions.NetworkServerNoClientException;
import tech.pcloud.proxy.network.server.model.ServiceStatus;
import tech.pcloud.proxy.network.server.utils.ServerCache;
import tech.pcloud.proxy.network.server.utils.ServerProtocolHelper;

import java.lang.reflect.Type;


/**
 * @ClassName RegisterResponseCommandService
 * @Author pandong
 * @Date 2019/1/30 14:25
 * 接收客户端的服务注册请求
 **/
public class RegisterServiceRequestCommandService
        implements CommandService<Service>, GetServiceNodeType, GetRequestType, GetNormalOperation, GetObjectContentObject<Service> {
    @Override
    public void execCommand(ProtocolPackage.Operation operation, ProtocolCommand command, Channel channel, Service content) throws Exception {
        try {
            Client client = ServerCache.INSTANCE.getClientWithId(content.getClientId());
            if (client == null) {
                String message = String.format("no client info, clientId[%d] is error,", content.getClientId());
                getLogger().warn(message);
                throw new NetworkServerNoClientException(message);
            }
            if (!ServerCache.INSTANCE.checkClientServiceStatus(client.getId(), content)) {
                ServiceStatus serviceStatus = ServerCache.INSTANCE.getClientServiceStatus(client.getId(), content);
                boolean isRegister = false;
                if (serviceStatus == null) {// 服务未注册
                    ServerCache.INSTANCE.addClientServiceMapping(client.getId(), content);
                    isRegister = true;
                } else if (!serviceStatus.isOpenProxyPort()) {// 服务已注册，但未打开端口
                    isRegister = true;
                }

                if (isRegister) {
                    getLogger().info("init proxy server with service[{}]", content.getName());
                    Server server = channel.parent().attr(NetworkModel.ChannelAttribute.SERVER).get();
                    ProxyServer proxyServer = new ProxyServer(server, content);
                    proxyServer.init();
                    ServerCache.INSTANCE.addProxyServerMapping(content.getProxyPort(), proxyServer);
                }

            }
            channel.writeAndFlush(ServerProtocolHelper.createRegisterSuccessResponseProtocol(NodeType.SERVICE, content));
            getLogger().info("register service success!");
        } catch (NetworkServerNoClientException e) {
            channel.writeAndFlush(ServerProtocolHelper.createExceptionResponseProtocol(e, content));
        }
    }

    @Override
    public Type getContentType() {
        return new TypeReference<Service>() {
        }.getType();
    }
}
