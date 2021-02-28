package metricserver.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import static io.netty.handler.codec.Delimiters.*;

public class MetricClientInitializer extends ChannelInitializer<SocketChannel> {
    private static final StringEncoder encoder = new StringEncoder();
    private static final StringDecoder decoder = new StringDecoder();

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        final var pipeline = ch.pipeline();
        pipeline.addLast("logging_handler", new LoggingHandler(LogLevel.INFO));

        //decoders
        pipeline.addLast("frame_decoder", new DelimiterBasedFrameDecoder(1000, lineDelimiter()));
        pipeline.addLast("string_decoder", decoder);

        pipeline.addLast("string_encoder", encoder);

        //
    }
}
