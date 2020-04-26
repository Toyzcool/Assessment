package domain;

public class Messier extends AstronomicalObject {
    // Fields
    private String number;
    private String constellation;
    private String description;

    // Get and Set methods
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override public String toString() {
        return "Messier{" + "catalogueNumber='" + number + '\'' + ", rightAscension='" + getRa() + '\'' + ", declination='" + getDecl() + '\'' + ", magnitude='" + getMagn() + '\'' + ", distance='" + getDistance() + '\'' + ", constellation='" + constellation + '\'' + ", description='" + description + '\'' + '}';
    }
}
