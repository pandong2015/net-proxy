package tech.pcloud.proxy.network.client.service.impl;

import com.alibaba.fastjson.TypeReference;
import io.netty.channel.Channel;
import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.core.Result;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.core.service.CommandService;
import tech.pcloud.proxy.network.core.service.adaptors.GetNormalOperation;
import tech.pcloud.proxy.network.core.service.adaptors.GetResponseType;
import tech.pcloud.proxy.network.core.service.adaptors.GetResultContentObject;
import tech.pcloud.proxy.network.core.service.adaptors.GetServiceNodeType;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

import java.lang.reflect.Type;


/**
 * @ClassName RegisterResponseCommandService
 * @Author pandong
 * @Date 2019/1/30 14:25
 * 接收服务端的返回信息（注册服务）
 **/
public class RegisterServiceResponseCommandService
        implements CommandService<Service>, GetServiceNodeType, GetResponseType, GetNormalOperation, GetResultContentObject<Service> {
    @Override
    public void execCommand(ProtocolPackage.Operation operation, ProtocolCommand command, Channel channel, Service content) {
        getLogger().info("register service success!");
        getLogger().debug("response service info:\n{}", content.toJson());
    }

    @Override
    public Type getContentType() {
        return new TypeReference<Result<Service>>() {
        }.getType();
    }
}
