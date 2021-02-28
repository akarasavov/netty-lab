package metricserver.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import static io.netty.handler.codec.Delimiters.lineDelimiter;

public class MetricInitializer extends ChannelInitializer<SocketChannel> {

    private static final StringEncoder encoder = new StringEncoder();
    private static final StringDecoder decoder = new StringDecoder();
    private final MetricServerCache cache = new MetricServerCache();

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        final var pipeline = ch.pipeline();

        //decoders
        pipeline.addLast("frame_decoder", new DelimiterBasedFrameDecoder(1000, lineDelimiter()));
        pipeline.addLast("string_decoder", decoder);

        pipeline.addLast("string_encoder", encoder);

        pipeline.addLast("message_handler", new MetricServerHandler(cache));
    }
}
