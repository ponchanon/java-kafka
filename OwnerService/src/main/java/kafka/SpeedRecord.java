package kafka;

public class SpeedRecord {

    private String licencePlate;

    private int cameraId;

    private double speed;

    public SpeedRecord(String licencePlate, int cameraId, double speed) {
        this.licencePlate = licencePlate;
        this.cameraId = cameraId;
        this.speed = speed;
    }

    public SpeedRecord() {
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
