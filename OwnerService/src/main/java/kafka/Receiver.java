package kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class Receiver {

    @Autowired
    private Sender sender;

    @Value("${app.topic.ownertopic}")
    private String ownertopc;

    @KafkaListener(topics = {"tofasttopic"})
    public void receive(@Payload SpeedRecord speedRecord,
                        @Headers MessageHeaders headers) {
        System.out.println("received message="+ speedRecord.toString());

        if (speedRecord.getSpeed() >= 72) {
            FeeRecord feeRecord = new FeeRecord(speedRecord);
            System.out.println("Sending fee record= " + speedRecord.toString());
            sender.send(ownertopc, feeRecord);
        }
    }

}