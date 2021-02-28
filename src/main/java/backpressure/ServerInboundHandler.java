package backpressure;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerInboundHandler extends ChannelInboundHandlerAdapter {

    int counter = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server receive msg=" + msg);
        counter++;
        boolean stopped = counter % 5 == 0;
        if (stopped) {
            ctx.write(MessageType.STOP);
            System.out.println("Server turn on backpressure");
        } else {
            MessageType message = MessageType.valueOf((String) msg);

            if (message != MessageType.NEW_MESSAGE) {
                System.out.println("Unsupported message");
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
}
