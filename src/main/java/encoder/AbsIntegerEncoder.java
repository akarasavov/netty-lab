package encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class AbsIntegerEncoder extends MessageToByteEncoder<ByteBuf> {
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
        while (in.readableBytes() >= 4) {
            final int absValue = Math.abs(in.readInt());
            out.writeInt(absValue);
        }
    }
}
