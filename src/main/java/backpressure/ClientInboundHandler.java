package backpressure;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientInboundHandler extends SimpleChannelInboundHandler<String> {
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("Client receive msg=" + msg);

        MessageType messageType = MessageType.valueOf(msg);
        if (messageType == MessageType.STOP) {
            System.out.println("Client should block sending");
        } else if (messageType == MessageType.START) {
            System.out.println("Client should start sending");
        }

    }
}
