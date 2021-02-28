package encoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbsIntegerEncoderTest {

    @Test
    void testEncoder() {
        final ByteBuf buffer = Unpooled.buffer();

        for (int i = 1; i < 10; i++) {
            buffer.writeInt(i * -1);
        }

        final EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        assertTrue(channel.writeOutbound(buffer));
        assertTrue(channel.finish());

        final ByteBuf outbound = channel.readOutbound();
        for (int i = 1; i < 10; i++) {
            final var readValue = outbound.readInt();
            System.out.println("Value=" + i + " readValue=" + readValue);
            assertEquals(i, readValue);
        }

    }

}