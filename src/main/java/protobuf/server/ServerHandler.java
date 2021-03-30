package protobuf.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static messages.TestProtocol.MessageRequest.*;
import static messages.TestProtocol.*;


public class ServerHandler extends SimpleChannelInboundHandler<MessageRequest>
{
    @Override
    protected void channelRead0( ChannelHandlerContext ctx, MessageRequest msg ) throws Exception
    {
        if ( msg.getType() == MessageRequest.Type.TIME )
        {
            System.out.println( "time request" );
            TimeResponse timeResponse = TimeResponse.newBuilder().setTime( System.currentTimeMillis() ).build();
            messages.TestProtocol.MessageResponse response = messages.TestProtocol.MessageResponse.newBuilder()
                                                                                                  .setType( messages.TestProtocol.MessageResponse.Type.TIME )
                                                                                                  .setTime( timeResponse ).build();
            ctx.write( response );
        }
        else if ( msg.getType() == MessageRequest.Type.HELLO )
        {
            System.out.println( "Serve receive msg " + msg.getHello().getMessageTxt() );
            messages.TestProtocol.SayHelloResponse sayHelloResponse = messages.TestProtocol.SayHelloResponse.newBuilder().setMessageTxt( "Hello I'm server" ).build();
            var response = messages.TestProtocol.MessageResponse.newBuilder().setType( messages.TestProtocol.MessageResponse.Type.HELLO )
                                                                .setHello( sayHelloResponse ).build();
            ctx.write( response );
        }
    }

    @Override
    public void channelReadComplete( ChannelHandlerContext ctx )
    {
        ctx.flush();
    }
}
