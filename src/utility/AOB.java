package utility;

import domain.AstronomicalObject;
import domain.Messier;
import domain.Planet;
import domain.Star;
import view.UserInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

public class AOB {
    // Field
    private String[] filename;
    private List<AstronomicalObject> aos;

    // Constructor
    public AOB() {
        this.filename = null;
        this.aos = new ArrayList<>();
    }

    public static void main(String[] args) {
        if (args.length != 3){
            System.out.println("Please enter three filenames");
            System.exit(1);
        }
        AOB aob = new AOB();
        aob.setFilename(args);
        try {
            aob.readFile(); // Read file and store objects into collections
            AstronomicalObjectDAO.setAllObjects(aob.getAos());
            new UserInterface();
        } catch (NumberFormatException e) {
            System.out.println("The data format is wrong:" + e.getMessage());
            System.exit(1);
        } catch (NoSuchElementException e){
            System.out.println("Some Collections is empty");
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.out.println("File:" + e.getMessage() + "can not found, please check file name");
            System.exit(1);
        } catch (NullPointerException e){
            System.out.println("The Collections is empty" + e.getMessage());
            System.exit(1);
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Read file and store data to objects
     * @throws NumberFormatException If the data can not covert to double
     */
    public void readFile() throws NumberFormatException,FileNotFoundException {
        Scanner scanStar, scanMessier, scanPlanet;
        String raw_data;
        String[] values;
        Set<String> identifyName = new HashSet<>();
        // Create scanner to scan file
        scanStar = new Scanner(new File(filename[0]));
        scanMessier = new Scanner(new File(filename[1]));
        scanPlanet = new Scanner(new File(filename[2]));

        // Read data about Star and store
        Star star;
        while (scanStar.hasNext()){
            raw_data = scanStar.nextLine();
            values = processData(raw_data, 1);
            // Check whether there is the same object or the data is wrong
            if (values.length != 7 || identifyName.contains(values[0])){
                System.out.println("There are some mistakes in Star file");
                System.exit(1);
            }
            star = new Star();
            star.setNumber(values[0]);
            star.setRa(Double.parseDouble(values[1]));
            star.setDecl(Double.parseDouble(values[2]));
            star.setMagn(Double.parseDouble(values[3]));
            star.setDistance(Double.parseDouble(values[4]));
            star.setType(values[5]);
            star.setConstellation(values[6]);
            // Add object to collection
            aos.add(star);
            identifyName.add(star.getNumber());
        }
        scanStar.close();

        // Read data about Messier and store
        Messier messier;
        while (scanMessier.hasNext()){
            raw_data = scanMessier.nextLine();
            values = processData(raw_data, 1);
            if (values.length < 6 || identifyName.contains('M' + values[0])){
                System.out.println("There are some mistakes in Messier file");
                System.exit(1);
            }
            messier = new Messier();
            messier.setNumber("M" + values[0]);
            messier.setRa(Double.parseDouble(values[1]));
            messier.setDecl(Double.parseDouble(values[2]));
            messier.setMagn(Double.parseDouble(values[3]));
            messier.setDistance(Double.parseDouble(values[4]));
            messier.setConstellation(values[5]);
            if (values.length == 7){
                messier.setDescription(values[6]);
            }else {
                messier.setDescription("-");
            }
            // Add object to collection
            aos.add(messier);
            identifyName.add(messier.getNumber());
        }
        scanMessier.close();

        // Read data about Planet and store
        Planet planet;
        while (scanPlanet.hasNext()){
            raw_data = scanPlanet.nextLine();
            values = processData(raw_data, 0);
            if (values.length != 6 || identifyName.contains(values[0])){
                System.out.println("There are some mistakes in Planet file");
                System.exit(1);
            }
            planet = new Planet();
            planet.setName(values[0]);
            planet.setRa(Double.parseDouble(values[1]));
            planet.setDecl(Double.parseDouble(values[2]));
            planet.setMagn(Double.parseDouble(values[3]));
            planet.setDistance(Double.parseDouble(values[4]));
            planet.setAlbedo(Double.parseDouble(values[5]));
            // Add object to collection
            aos.add(planet);
            identifyName.add(planet.getName());
        }
        scanPlanet.close();
    }

    /**
     * Process data read from files
     * @param raw_data The data needed to separate
     * @param type  distinguish separating by "|" or whitespace
     * @return  The data had been separated
     */
    public String[] processData(String raw_data, int type){
        String[] values;
        Pattern pattern = null;
        raw_data = raw_data.trim();
        // Use "|" to separate data
        if (type == 1){
            pattern = Pattern.compile("\\|");
        }else if (type == 0){
            // Use whitespace to separate data
            pattern = Pattern.compile("\\s+");
        }else {
            System.out.println("Please use correct type");
            System.exit(1);
        }
        values = pattern.split(raw_data);
        // Delete whitespace
        if (values != null) {
            for (int i = 0 ; i < values.length ; i++){
                values[i] = values[i].trim();
                if (values[i].equals("")){
                    System.out.println("File is missing data");
                    System.exit(1);
                }
            }
        }
        return values;
    }

    // Get and set methods
    public String[] getFilename() {
        return filename;
    }

    public void setFilename(String[] filename) {
        this.filename = filename;
    }

    public List<AstronomicalObject> getAos(){
        return aos;
    }

    public void setAos(List<AstronomicalObject> aos) {
        this.aos = aos;
    }
}
