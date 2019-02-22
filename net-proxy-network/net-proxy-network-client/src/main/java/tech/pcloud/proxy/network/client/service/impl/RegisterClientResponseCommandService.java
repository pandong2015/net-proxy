package tech.pcloud.proxy.network.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.netty.channel.Channel;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.model.NodeType;
import tech.pcloud.proxy.core.Result;
import tech.pcloud.proxy.network.client.exceptions.NetworkClientParseCommandException;
import tech.pcloud.proxy.network.client.model.ClientInfo;
import tech.pcloud.proxy.network.client.utils.ClientCache;
import tech.pcloud.proxy.network.core.protocol.Operation;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.core.service.CommandService;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

import java.net.InetSocketAddress;


/**
 * @ClassName RegisterResponseCommandService
 * @Author pandong
 * @Date 2019/1/30 14:25
 * 接收服务端的返回信息（注册客户端）
 **/
public class RegisterClientResponseCommandService implements CommandService<Client> {
    @Override
    public void execCommand(ProtocolPackage.Operation operation, ProtocolCommand command, Channel channel, Client content) {
        getLogger().debug("response client info:{}", content.toJson());
        InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.localAddress();
        ClientInfo clientInfo = ClientCache.getClientInfoWithPort(inetSocketAddress.getPort());
        if (clientInfo != null) {
            clientInfo.setId(content.getId());
            getLogger().info("register client success, client id : [{}]!", content.getId());
        } else {
            getLogger().warn("register client fail, no client info.");
        }
    }

    @Override
    public ProtocolPackage.RequestType getRequestType() {
        return ProtocolPackage.RequestType.RESPONSE;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.CLIENT;
    }

    @Override
    public int getOperation() {
        return Operation.NORMAL.getOperation();
    }

    @Override
    public Client getContentObject(String content) {
        Result<Client> result = JSON.parseObject(content, new TypeReference<Result<Client>>() {
        });
        if (result.getCode() != 0) {
            throw new NetworkClientParseCommandException("response is error, response code:" + result.getCode());
        }
        return result.getData();
    }
}
