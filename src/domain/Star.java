package domain;

public class Star extends AstronomicalObject {
    // Fields
    private String number;
    private String type;
    private String constellation;

    // Get and Set methods
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    @Override public String toString() {
        return "Star{" + "catalogueNumber='" + number + '\'' + ", rightAscension='" + getRa() + '\'' + ", declination='" + getDecl() + '\'' + ", magnitude='" + getMagn() + '\'' + ", distance='" + getDistance() + '\'' + ", starType='" + type
            + '\'' + ", constellation='" + constellation + '\'' + '}';
    }
}
