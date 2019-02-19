package tech.pcloud.proxy.network.server.service.impl;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import tech.pcloud.proxy.configure.model.NodeType;
import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.network.core.protocol.Operation;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.core.service.CommandService;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;


/**
 * @ClassName RegisterResponseCommandService
 * @Author pandong
 * @Date 2019/1/30 14:25
 * 接收客户端的服务注册请求
 **/
public class RegisterServiceRequestCommandService implements CommandService<Service> {
    @Override
    public void execCommand(ProtocolPackage.Operation operation, ProtocolCommand command, Channel channel, Service content) {
        getLogger().info("register service success!");
        getLogger().debug("request service info:\n{}", content.toJson());
    }

    @Override
    public ProtocolPackage.RequestType getRequestType() {
        return ProtocolPackage.RequestType.REQUEST;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.SERVICE;
    }

    @Override
    public int getOperation() {
        return Operation.NORMAL.getOperation();
    }

    @Override
    public Service getContentObject(String content) {
        return JSON.parseObject(content, Service.class);
    }
}
