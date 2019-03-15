package tech.pcloud.proxy.network.client.service.impl;

import com.alibaba.fastjson.TypeReference;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.core.Result;
import tech.pcloud.proxy.network.client.model.ClientInfo;
import tech.pcloud.proxy.network.client.utils.ClientCache;
import tech.pcloud.proxy.network.client.utils.ClientProtocolHelper;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.core.service.CommandService;
import tech.pcloud.proxy.network.core.service.adaptors.GetClientNodeType;
import tech.pcloud.proxy.network.core.service.adaptors.GetNormalOperation;
import tech.pcloud.proxy.network.core.service.adaptors.GetResponseType;
import tech.pcloud.proxy.network.core.service.adaptors.GetResultContentObject;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

import java.lang.reflect.Type;
import java.net.InetSocketAddress;


/**
 * @ClassName RegisterResponseCommandService
 * @Author pandong
 * @Date 2019/1/30 14:25
 * 接收服务端的返回信息（注册客户端）
 **/
public class RegisterClientResponseCommandService
        implements CommandService<Client>, GetClientNodeType, GetResponseType, GetNormalOperation, GetResultContentObject<Client> {
    @Override
    public void execCommand(ProtocolPackage.Operation operation, ProtocolCommand command, Channel channel, Client content) throws Exception {
        getLogger().debug("response client info:{}", content.toJson());
        InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.localAddress();
        ClientInfo clientInfo = ClientCache.getClientInfoWithPort(inetSocketAddress.getPort());
        if (clientInfo != null) {
            clientInfo.setId(content.getId());
            getLogger().info("register client success, client id : [{}]!", content.getId());
            clientInfo.getServices().forEach(service -> {
                service.setClientId(clientInfo.getId());
                channel.writeAndFlush(ClientProtocolHelper.createRegisterServiceRequestProtocol(service)).addListener(new ChannelFutureListener() {

                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.isSuccess()) {
                            getLogger().info("send register service success.");
                        } else {
                            getLogger().warn("send register service fail.");
                        }
                    }
                });
            });
        } else {
            getLogger().warn("register client fail, no client info.");
        }
    }

    @Override
    public Type getContentType() {
        return new TypeReference<Result<Client>>() {
        }.getType();
    }
}
