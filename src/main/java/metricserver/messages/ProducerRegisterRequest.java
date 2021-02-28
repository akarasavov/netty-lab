package metricserver.messages;

public class ProducerRegisterRequest implements Message {

    public final String producerName;

    public ProducerRegisterRequest(String producerName) {
        this.producerName = producerName;
    }

    public static ProducerRegisterRequest decode(String value){
        return new ProducerRegisterRequest(value);
    }

    public static String encode(ProducerRegisterRequest request){
        return request.producerName;
    }
}

