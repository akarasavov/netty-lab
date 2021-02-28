package metricserver;

import io.netty.channel.Channel;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProducerContext {

    public final SocketAddress socketAddress;
    private State state;
    private final Map<String, String> metricMap = new HashMap<>();

    public ProducerContext(Channel channel) {
        this.socketAddress = channel.remoteAddress();
        this.state = State.CONNECTED;
    }

    private void switchState(State state, Set<State> expectedState) {
        if (!expectedState.contains(this.state)) {
            throw new IllegalArgumentException(String.format("State %s don't match the list %s", this.state, expectedState));
        }
        System.out.printf("Producer %s switch from %s to %s %n", socketAddress, this.state, state);
        this.state = state;
    }

    public void addMetric(String key, String value) {
        switchState(State.AWAIT_METRICS, Set.of(State.CONNECTED, State.AWAIT_METRICS));
        System.out.printf("Receive metrics for producer key=%s value=%s %n", key, value);
        metricMap.put(key, value);
    }

    public void finishAddingMetrics() {
        switchState(State.FINISH_ADDING_METRICS, Set.of(State.AWAIT_METRICS));
    }

    private enum State {
        CONNECTED,
        AWAIT_METRICS,
        FINISH_ADDING_METRICS
    }

}
