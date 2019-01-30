package tech.pcloud.proxy.network.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.netty.channel.Channel;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.model.NodeType;
import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.core.Result;
import tech.pcloud.proxy.network.client.exceptions.NetworkClientParseCommandException;
import tech.pcloud.proxy.network.client.service.CommandService;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;


/**
 * @ClassName RegisterResponseCommandService
 * @Author pandong
 * @Date 2019/1/30 14:25
 **/
public class RegisterServiceResponseCommandService implements CommandService<Service> {
    @Override
    public void execCommand(ProtocolPackage.Operation operation, ProtocolCommand command, Channel channel, Service content) {
        getLogger().info("register service success!");
        getLogger().debug("response service info:\n{}", content.toJson());
    }

    @Override
    public ProtocolPackage.RequestType getRequestType() {
        return ProtocolPackage.RequestType.RESPONSE;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.SERVICE;
    }

    @Override
    public Service getContentObject(String content) {
        Result<Service> result = JSON.parseObject(content, new TypeReference<Result<Service>>() {
        });
        if (result.getCode() != 0) {
            throw new NetworkClientParseCommandException("response is error, response code:" + result.getCode());
        }
        return result.getData();
    }
}
