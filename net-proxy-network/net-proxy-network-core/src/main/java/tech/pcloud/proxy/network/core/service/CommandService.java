package tech.pcloud.proxy.network.core.service;

import com.alibaba.fastjson.TypeReference;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.pcloud.proxy.configure.model.NodeType;
import tech.pcloud.proxy.core.ProxyException;
import tech.pcloud.proxy.core.model.ToJson;
import tech.pcloud.proxy.core.util.ResultGenerate;
import tech.pcloud.proxy.network.core.protocol.ManageProtocolBody;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.core.utils.ProtocolHelper;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @ClassName CommandService
 * @Author pandong
 * @Date 2019/1/30 14:22
 **/
public interface CommandService<T extends ToJson> extends GetNodeType, GetIOType, GetOperation, GetContentObject<T> {

    default void execute(ProtocolPackage.Operation operation, ProtocolCommand command, Channel channel, String content) throws Exception{
        if (!verify(operation, command, content)) {
            getLogger().info("verify fail.");
            return;
        }
        T t = getContentObject(content);
        try {
            execCommand(operation, command, channel, t);
        }catch (Exception e){
            throw e;
        }

    }

    void execCommand(ProtocolPackage.Operation operation, ProtocolCommand command, Channel channel, T content) throws Exception;

    default boolean verify(ProtocolPackage.Operation operation, ProtocolCommand command, String content) {
        if (operation == null || command == null || content == null) {
            return false;
        }
        if (operation.getType() != getRequestType()
                && operation.getOperation() != getOperation()
                && command.getNodeType() != getNodeType()) {
            return false;
        }
        return true;
    }

    default Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }
}
