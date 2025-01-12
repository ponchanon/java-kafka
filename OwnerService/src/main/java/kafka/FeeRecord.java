package kafka;

public class FeeRecord {

    private String licencePlate;

    private double speed;

    private String owner;

    private int fine;

    public FeeRecord(SpeedRecord speedRecord) {
        this.licencePlate = speedRecord.getLicencePlate();
        this.speed = speedRecord.getSpeed();
        this.owner = this.licencePlate + " owner ";
        this.fine = this.calculateFine(this.speed);
    }

    private int calculateFine(double speed) {
        if (speed>90)
            return 125;
        else if (speed>82)
            return 80;
        else if (speed>77)
            return 45;
        else if (speed>72)
            return 25;
        else
            return 0;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    @Override
    public String toString() {
        return "FeeRecord{" +
                "licencePlate='" + licencePlate + '\'' +
                ", speed=" + speed +
                ", owner='" + owner + '\'' +
                ", fine=" + fine +
                '}';
    }
}
