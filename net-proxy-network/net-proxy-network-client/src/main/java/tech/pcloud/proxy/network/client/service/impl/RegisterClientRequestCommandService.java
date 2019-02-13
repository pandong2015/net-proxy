package tech.pcloud.proxy.network.client.service.impl;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.model.NodeType;
import tech.pcloud.proxy.network.core.service.CommandService;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;


/**
 * @ClassName RegisterResponseCommandService
 * @Author pandong
 * @Date 2019/1/30 14:25
 **/
public class RegisterClientRequestCommandService implements CommandService<Client> {
    @Override
    public void execCommand(ProtocolPackage.Operation operation, ProtocolCommand command, Channel channel, Client content) {
        getLogger().info("register client success!");
        getLogger().debug("request client info:\n{}", content.toJson());
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
    public Client getContentObject(String content) {
        return JSON.parseObject(content, Client.class);
    }
}