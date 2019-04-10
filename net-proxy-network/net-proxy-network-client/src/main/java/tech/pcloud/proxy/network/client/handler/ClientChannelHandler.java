package tech.pcloud.proxy.network.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.network.core.service.ServiceKey;
import tech.pcloud.proxy.network.core.service.CommandService;
import tech.pcloud.proxy.network.core.service.CommandServiceFactory;
import tech.pcloud.proxy.network.core.NetworkModel;
import tech.pcloud.proxy.network.core.protocol.Operation;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

@Slf4j
public class ClientChannelHandler extends SimpleChannelInboundHandler<String> {
    private CommandServiceFactory commandServiceFactory;

    public ClientChannelHandler(CommandServiceFactory commandServiceFactory) {
        this.commandServiceFactory = commandServiceFactory;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        ProtocolPackage.Operation operation = ctx.channel().attr(NetworkModel.ChannelAttribute.OPERATION).get();
        if (Operation.NORMAL.ordinal() == operation.getOperation()
                || Operation.TRANSFER_REQUEST.ordinal() == operation.getOperation()) {
            ProtocolCommand command = ctx.channel().attr(NetworkModel.ChannelAttribute.COMMAND).get();
            ServiceKey key = new ServiceKey(operation.getOperation(), operation.getType(), command.getNodeType());
            CommandService<String> service = commandServiceFactory.getCommandService(key);
            service.execute(operation, command, ctx.channel(), msg);
        }
    }

}
