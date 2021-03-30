package protobuf.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server
{
    static final int PORT = Integer.parseInt( System.getProperty( "port", "8007" ) );

    public static void main( String[] args ) throws InterruptedException
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup( 1 );
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {
            ServerBootstrap b = new ServerBootstrap();
            b.group( bossGroup, workerGroup )
             .channel( NioServerSocketChannel.class )
             .handler( new LoggingHandler( LogLevel.INFO ) )
             .childHandler( new ServerInitializer() );

            b.bind( PORT ).sync().channel().closeFuture().sync();
        }
        finally
        {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
