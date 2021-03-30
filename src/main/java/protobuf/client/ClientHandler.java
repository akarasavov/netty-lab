package protobuf.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static messages.TestProtocol.MessageResponse;

public class ClientHandler extends SimpleChannelInboundHandler<MessageResponse>
{
    @Override
    protected void channelRead0( ChannelHandlerContext ctx, MessageResponse msg ) throws Exception
    {
        if ( msg.getType() == MessageResponse.Type.TIME )
        {
            System.out.println( "Client receive time " + msg.getTime().getTime() );
        }
        else if ( msg.getType() == MessageResponse.Type.HELLO )
        {
            System.out.println( "Client receive hello response " + msg.getHello().getMessageTxt() );
        }
    }
}
