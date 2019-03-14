package tech.pcloud.proxy.network.server.service.impl;

import com.alibaba.fastjson.TypeReference;
import io.netty.channel.Channel;
import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.core.service.CommandService;
import tech.pcloud.proxy.network.core.service.adaptors.GetObjectContentObject;
import tech.pcloud.proxy.network.core.service.adaptors.GetNormalOperation;
import tech.pcloud.proxy.network.core.service.adaptors.GetRequestType;
import tech.pcloud.proxy.network.core.service.adaptors.GetServiceNodeType;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

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
    public void execCommand(ProtocolPackage.Operation operation, ProtocolCommand command, Channel channel, Service content) {
//        getLogger().info("register service success!");
//        getLogger().debug("request service info:\n{}", content.toJson());
    }

    @Override
    public Type getContentType() {
        return new TypeReference<Service>() {
        }.getType();
    }
}
