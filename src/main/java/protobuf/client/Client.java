package protobuf.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import static messages.TestProtocol.*;
import static messages.TestProtocol.MessageRequest;
import static messages.TestProtocol.SayHelloRequest;

public class Client
{
    static final String HOST = System.getProperty( "host", "127.0.0.1" );
    static final int PORT = Integer.parseInt( System.getProperty( "port", "8007" ) );

    public static void main( String[] args ) throws InterruptedException
    {
        EventLoopGroup group = new NioEventLoopGroup();

        try
        {
            Bootstrap b = new Bootstrap();
            b.group( group )
             .channel( NioSocketChannel.class )
             .handler( new ClientInitializer() );

            Channel ch = b.connect( HOST, PORT ).sync().channel();
            SayHelloRequest sayHelloResponse = SayHelloRequest.newBuilder().setMessageTxt( "Hello I'm client" ).build();
            TimeRequest timeRequest = TimeRequest.newBuilder().build();
            MessageRequest request = MessageRequest.newBuilder().setType( MessageRequest.Type.TIME )
                                                    .setTime( timeRequest ).build();

            ch.writeAndFlush( request );

            Thread.sleep( 10000 );
        }
        finally
        {
            group.shutdownGracefully();
        }
    }
}
