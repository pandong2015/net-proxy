package tech.pcloud.proxy.network.core.service;

import com.alibaba.fastjson.TypeReference;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @ClassName CommandService
 * @Author pandong
 * @Date 2019/1/30 14:22
 **/
public interface CommandService<T> extends GetNodeType, GetIOType, GetOperation, GetContentObject<T> {

    default void execute(ProtocolPackage.Operation operation, ProtocolCommand command, Channel channel, String content) {
        if (verify(operation, command, content)) {
            T t = getContentObject(content);
            execCommand(operation, command, channel, t);
        } else {
            getLogger().info("verify fail.");
        }
    }

    void execCommand(ProtocolPackage.Operation operation, ProtocolCommand command, Channel channel, T content);

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
