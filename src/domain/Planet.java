package domain;

public class Planet extends AstronomicalObject {
    // Fields
    private String name;
    private double albedo;

    // Get and Set methods
    public String getName() {
        return name;
    }

    public double getAlbedo() {
        return albedo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlbedo(double albedo) {
        this.albedo = albedo;
    }

    @Override public String toString() {
        return "Planet{" + "name='" + name + '\'' + ", rightAscension='" + getRa() + '\'' + ", declination='" + getDecl() + '\'' + ", magnitude='" + getMagn() + '\'' + ", distance='" + getDistance() + '\'' + ", albedo='" + albedo + '\'' + '}';
    }
}
