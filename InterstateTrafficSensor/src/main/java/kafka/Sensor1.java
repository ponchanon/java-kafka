package kafka;

import java.time.LocalTime;
import java.util.List;
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
        new CarConfig("AA", 25000),
        new CarConfig("BB", 22900),
        new CarConfig("CC", 26500),
        new CarConfig("EE", 22000),
        new CarConfig("FF", 26000),
        new CarConfig("GA", 25600),
        new CarConfig("FB", 26200),
        new CarConfig("FA", 27200),
        new CarConfig("FG", 27500)
    );

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