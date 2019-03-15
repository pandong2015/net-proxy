package tech.pcloud.proxy.network.server.service.impl;

import com.alibaba.fastjson.TypeReference;
import io.netty.channel.Channel;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.core.service.CommandService;
import tech.pcloud.proxy.network.core.service.adaptors.GetClientNodeType;
import tech.pcloud.proxy.network.core.service.adaptors.GetNormalOperation;
import tech.pcloud.proxy.network.core.service.adaptors.GetObjectContentObject;
import tech.pcloud.proxy.network.core.service.adaptors.GetRequestType;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;
import tech.pcloud.proxy.network.server.utils.ServerCache;
import tech.pcloud.proxy.network.server.utils.ServerProtocolHelper;

import java.lang.reflect.Type;
import java.net.InetSocketAddress;


/**
 * @ClassName RegisterResponseCommandService
 * @Author pandong
 * @Date 2019/1/30 14:25
 * 接收客户端的注册请求
 **/
public class RegisterClientRequestCommandService
        implements CommandService<Client>, GetClientNodeType, GetRequestType, GetNormalOperation, GetObjectContentObject<Client> {
    @Override
    public void execCommand(ProtocolPackage.Operation operation, ProtocolCommand command, Channel channel, Client content) throws Exception{
        getLogger().debug("request client info:{}", content.toJson());
        InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.localAddress();
        content.setHost(inetSocketAddress.getHostString());
        ServerCache.INSTANCE.addClientChannelMapping(content, channel);
        getLogger().info("register client success!");
        channel.writeAndFlush(ServerProtocolHelper.createRegisterSuccessResponseProtocol(content));
    }

    @Override
    public Type getContentType() {
        return new TypeReference<Client>() {
        }.getType();
    }
}
