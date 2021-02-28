package metricserver.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

//producer_register_req
//send_metric_req - send_metric_req_start,metric_key1:value1,metric_key2:value2,metric_key3:value3,send_metric_req_end

public class MetricServerHandler extends SimpleChannelInboundHandler<String> {

    private MetricServerCache metricCache;
    private boolean isFirst = true;

    public MetricServerHandler(MetricServerCache metricCache) {
        this.metricCache = metricCache;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        metricCache.addNewConnectedProducer(ctx.channel());
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        final var tokens = msg.split(",");
        final Map<String, String> keyValueMap;
        if (isFirst) {
            keyValueMap = parseMetrics(tokens, 1);
        } else {
            keyValueMap = parseMetrics(tokens, 0);
        }
        metricCache.addNewMetric(ctx.channel(), keyValueMap);

        if (isFirst) {
            isFirst = false;
        }
    }

    private Map<String, String> parseMetrics(String[] tokens, int skip) {
        Map<String, String> keyValueMap = new HashMap<>();
        for (int i = skip; i < tokens.length; i++) {
            final var token = tokens[i];
            final var keyValueEntity = token.split(":");
            if (keyValueEntity.length != 2) {
                throw new IllegalArgumentException(token + " is in illegal state");
            }
            keyValueMap.put(keyValueEntity[0], keyValueEntity[1]);
        }
        return keyValueMap;
    }
}
