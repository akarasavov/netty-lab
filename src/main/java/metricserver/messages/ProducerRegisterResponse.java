package metricserver.messages;

public class ProducerRegisterResponse {

    private final Status status;

    public ProducerRegisterResponse(Status status) {
        this.status = status;
    }

    public boolean isOk(){
        return status == Status.OK;
    }

    public static ProducerRegisterResponse encode(String status){
        return new ProducerRegisterResponse(Status.valueOf(status));
    }

    public static String decode(ProducerRegisterResponse response){
        return response.status.name();
    }

    private enum Status{
        OK,
        REJECTED
    }
}
