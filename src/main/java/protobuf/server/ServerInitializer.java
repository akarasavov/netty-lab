package protobuf.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import messages.TestProtocol;

import static messages.TestProtocol.*;

public class ServerInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel( SocketChannel ch ) throws Exception
    {
        ChannelPipeline p = ch.pipeline();

        p.addLast( new ProtobufVarint32FrameDecoder() );
        p.addLast( new ProtobufDecoder( MessageRequest.getDefaultInstance() ) );

        p.addLast( new ProtobufVarint32LengthFieldPrepender() );
        p.addLast( new ProtobufEncoder() );

        p.addLast( new ServerHandler() );
    }
}
