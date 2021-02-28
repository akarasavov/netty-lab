package uploadfile;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UploadFileClient {

    static final String HOST = System.getProperty("host", "127.0.0.1");

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        EventLoopGroup eg = new NioEventLoopGroup();

        try {
            Bootstrap bc = new Bootstrap();
            bc.group(eg)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO))
                                    .addLast(new ClientInboundHandler());
                        }
                    });
            Channel ch = bc.connect(HOST, UploadFileServer.PORT).sync().channel();
            final var scanner = new Scanner(System.in);
            while (true) {
                final var line = scanner.nextLine();
                System.out.println("Client will send " + line);

                ch.writeAndFlush(line).sync();
            }
        } finally {
            eg.shutdownGracefully();
        }

    }
}
