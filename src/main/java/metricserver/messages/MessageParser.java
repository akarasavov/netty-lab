package metricserver.messages;

public class MessageParser {
    private final State state;

    public MessageParser() {
        this.state = State.INITIAL;
    }

    public Message parse(String message){
        return null;
    }

    private static enum State{
        INITIAL,
        WAITING_METRICS
    }

}
