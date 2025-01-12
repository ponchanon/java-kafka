package kafka;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Sensor1 {

    @Autowired
    private Sender sender;

    private static class CarConfig {
        String licensePrefix;
        AtomicInteger cameraId;
        AtomicInteger licensePlateNumber;
        int interval;
        
        public CarConfig(String licensePrefix, int interval) {
            this.licensePrefix = licensePrefix;
            this.cameraId = new AtomicInteger(1);
            this.licensePlateNumber = new AtomicInteger(1000);
            this.interval = interval;
        }
    }

    private final List<CarConfig> carConfigs = List.of(
        new CarConfig("AA", getRandomFixedRate()),
        new CarConfig("BB", getRandomFixedRate()),
        new CarConfig("CC", getRandomFixedRate()),
        new CarConfig("EE", getRandomFixedRate()),
        new CarConfig("FF", getRandomFixedRate()),
        new CarConfig("GA", getRandomFixedRate()),
        new CarConfig("FB", getRandomFixedRate()),
        new CarConfig("FA", getRandomFixedRate()),
        new CarConfig("FG", getRandomFixedRate())
    );

    private int getRandomFixedRate() {
        return ThreadLocalRandom.current().nextInt(22000, 30001); // Generates random value between 22000 and 30000 inclusive
    }

    @Scheduled(fixedRate = 1000) // Tick every second to simulate dynamic scheduling
    public void scheduleTasks() {
        carConfigs.forEach(config -> {
            if (System.currentTimeMillis() % config.interval < 1000) {
                sendSensorData(config);
            }
        });
    }

    private void sendSensorData(CarConfig config) {
        String cameratopic = "cameratopic" + config.cameraId.get();
        String licensePlate = config.licensePrefix + config.licensePlateNumber.get();
        SensorRecord sensorRecord = new SensorRecord(licensePlate, LocalTime.now().getMinute(), LocalTime.now().getSecond(), config.cameraId.get());

        sender.send(cameratopic, sensorRecord);
        System.out.println("Sending to topic: " + cameratopic + "  " + sensorRecord.cameraId + " " + sensorRecord.licencePlate);

        config.cameraId.getAndIncrement();
        if (config.cameraId.get() == 3) {
            config.cameraId.set(1);
            config.licensePlateNumber.getAndIncrement();
        }
    }
}