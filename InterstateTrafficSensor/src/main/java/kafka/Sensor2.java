package kafka;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Sensor2 {

    @Autowired
    private Sender sender;

    static class CarConfig {
        int cameraId = 1;
        int licencePlateNumber = 2000;
        String prefix;
        int fixedRate;

        public CarConfig(String prefix, int fixedRate) {
            this.prefix = prefix;
            this.fixedRate = fixedRate;
        }
    }

    private final List<CarConfig> carConfigs = new ArrayList<>();

    public Sensor2() {
        carConfigs.add(new CarConfig("AS", 25800));
        carConfigs.add(new CarConfig("BS", 23900));
        carConfigs.add(new CarConfig("CS", 26100));
        carConfigs.add(new CarConfig("ES", 22900));
        carConfigs.add(new CarConfig("FS", 26700));
        carConfigs.add(new CarConfig("GS", 25900));
        carConfigs.add(new CarConfig("FBS", 24200));
        carConfigs.add(new CarConfig("FAS", 23200));
        carConfigs.add(new CarConfig("FGS", 28500));
    }

    @Scheduled(fixedRate = 1000)
    public void sendSensorData() {
        for (CarConfig carConfig : carConfigs) {
            if (System.currentTimeMillis() % carConfig.fixedRate < 1000) { // Simulate fixedRate scheduling
                sendCarData(carConfig);
            }
        }
    }

    private void sendCarData(CarConfig carConfig) {
        String cameratopic = "cameratopic" + carConfig.cameraId;
        String licencePlate = carConfig.prefix + carConfig.licencePlateNumber;

        SensorRecord sensorRecord = new SensorRecord(
            licencePlate,
            LocalTime.now().getMinute(),
            LocalTime.now().getSecond(),
            carConfig.cameraId
        );

        sender.send(cameratopic, sensorRecord);
        System.out.println("Sending to topic: " + cameratopic + " " + sensorRecord.cameraId + " " + sensorRecord.licencePlate);

        // Update camera ID and license plate number
        carConfig.cameraId++;
        if (carConfig.cameraId == 3) {
            carConfig.cameraId = 1;
            carConfig.licencePlateNumber++;
        }
    }
}
