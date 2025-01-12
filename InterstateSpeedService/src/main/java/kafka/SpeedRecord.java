package kafka;

public class SpeedRecord {

    private String licencePlate;

    private int cameraId;

    private double speed;

    public SpeedRecord(SensorRecord sensorRecord) {
        this.licencePlate = sensorRecord.licencePlate;
        this.cameraId = sensorRecord.cameraId;
        this.speed = this.calculateSpeed(sensorRecord.minute, sensorRecord.second);
    }

    private double calculateSpeed(int min, int sec) {
        int totalSec = min * 60 + sec;
        return (0.5/totalSec) * 3600;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "SpeedRecord{" +
                "licencePlate='" + licencePlate + '\'' +
                ", cameraId=" + cameraId +
                ", speed=" + speed +
                '}';
    }
}
