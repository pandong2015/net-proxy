package tech.pcloud.proxy.network.server.service.impl;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.model.NodeType;
import tech.pcloud.proxy.core.service.IdGenerateService;
import tech.pcloud.proxy.network.core.protocol.Operation;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.core.service.CommandService;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;
import tech.pcloud.proxy.network.server.utils.ServerCache;
import tech.pcloud.proxy.network.server.utils.ServerUtil;


/**
 * @ClassName RegisterResponseCommandService
 * @Author pandong
 * @Date 2019/1/30 14:25
 * 接收客户端的注册请求
 **/
public class RegisterClientRequestCommandService implements CommandService<Client> {
    @Override
    public void execCommand(ProtocolPackage.Operation operation, ProtocolCommand command, Channel channel, Client content) {
        getLogger().debug("request client info:\n{}", content.toJson());
        content.setId(IdGenerateService.generate(IdGenerateService.IdType.CLIENT));
        ServerCache.INSTANCE.addClientChannelMapping(content, channel);
        getLogger().info("register client success!");
        channel.writeAndFlush(ServerUtil.successResult(content));
    }

    @Override
    public ProtocolPackage.RequestType getRequestType() {
        return ProtocolPackage.RequestType.REQUEST;
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
        return JSON.parseObject(content, Client.class);
    }
}
