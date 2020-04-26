package domain;

public abstract class AstronomicalObject {
    // Fields
    private double distance;
    private double magn;
    private double ra;
    private double decl;

    // Get and Set methods
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getMagn() {
        return magn;
    }

    public void setMagn(double magn) {
        this.magn = magn;
    }

    public double getRa() {
        return ra;
    }

    public void setRa(double ra) {
        this.ra = ra;
    }

    public double getDecl() {
        return decl;
    }

    public void setDecl(double decl) {
        this.decl = decl;
    }

    @Override public String toString() {
        return "AstronomicalObject{" + "distance=" + distance + ", magnitude=" + magn
            + ", ra=" + ra + ", declination=" + decl + '}';
    }

}
