package metricserver.server;

import io.netty.channel.Channel;
import metricserver.ProducerContext;

import java.net.SocketAddress;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MetricServerCache {

    private Map<SocketAddress, ProducerContext> remoteAddressContextMap = new ConcurrentHashMap<>();


    public void addNewConnectedProducer(Channel channel) {
        final var removedContext = Optional.ofNullable(remoteAddressContextMap.remove(channel.remoteAddress()));
        removedContext.ifPresent(context -> {
            System.out.printf("Client with address=%s is connected once again %n", context.socketAddress);
        });

        remoteAddressContextMap.put(channel.remoteAddress(), new ProducerContext(channel));
    }

    public void addNewMetric(Channel channel, Map<String, String> keyValueMap) {
        remoteAddressContextMap.computeIfPresent(channel.remoteAddress(), (address, context) -> {
            keyValueMap.forEach((key, value) -> {
                System.out.printf("Add metrics for socket address %s key=%s value=%s %n", channel.remoteAddress(), key, value);
                context.addMetric(key, value);
            });
            return context;
        });
    }
}

