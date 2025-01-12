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

    @Value("${app.topic.tofasttopic}")
    private String speedTopic;

    @KafkaListener(topics = {"cameratopic1" , "cameratopic2"})
    public void receive(@Payload SensorRecord sensorRecord,
                        @Headers MessageHeaders headers) {
        System.out.println("received message="+ sensorRecord.toString());

        SpeedRecord speedRecord = new SpeedRecord(sensorRecord);
        System.out.println("Sending speed record= " + speedRecord.toString());
        sender.send(speedTopic, speedRecord);
    }

}
