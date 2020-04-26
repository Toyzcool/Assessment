package utility;

import domain.AstronomicalObject;
import domain.Messier;
import domain.Planet;
import domain.Star;
import vo.Condition;
import vo.Query;

import java.util.*;
import java.util.stream.Collectors;

public class AstronomicalObjectDAO implements DAO {
    // Store the copy of collection of AstronomicalObject objects
    private static List<AstronomicalObject> allObjects;

    @Override public Map<String,Vector<Vector<String>>> get(Query query) {
        List<AstronomicalObject> aos = getAllObjects(); // Get all the objects
        if (query == null){
            return processAllObjects(aos);
        }
        switch (query.getType()){
            // Search stars
            case "Star":
                aos = aos.stream().filter(s -> s instanceof Star).collect(Collectors.toList());
                for (Condition condition : query.getConditions()) {
                    switch (condition.getProperty()){
                        case "ra": case "decl": case "magn": case "distance":
                            aos = genCondition(aos,condition);
                            break;
                        case "number":
                            switch (condition.getOperator()){
                                case "=":
                                    aos = aos.stream()
                                        .filter(s -> ((Star) s).getNumber().equals(condition.getValue())).collect(
                                            Collectors.toList());
                                    break;
                                case "!=":
                                    aos = aos.stream().filter(s -> (!((Star) s).getNumber().equals(condition.getValue()))).collect(
                                        Collectors.toList());
                                    break;
                            }
                            break;
                        case "type":
                            switch (condition.getOperator()){
                                case "=":
                                    aos = aos.stream()
                                        .filter(s -> ((Star) s).getType().equals(condition.getValue())).collect(
                                            Collectors.toList());
                                    break;
                                case "!=":
                                    aos = aos.stream()
                                        .filter(s -> (!((Star) s).getType().equals(condition.getValue()))).collect(
                                            Collectors.toList());
                                    break;
                            }
                            break;
                        case "constellation":
                            switch (condition.getOperator()){
                                case "=":
                                    aos = aos.stream()
                                        .filter(s -> ((Star) s).getConstellation().equals(condition.getValue())).collect(
                                            Collectors.toList());
                                    break;
                                case "!=":
                                    aos = aos.stream()
                                        .filter(s -> (!((Star) s).getConstellation().equals(condition.getValue()))).collect(
                                            Collectors.toList());
                                    break;
                            }
                            break;
                    }
                }
                break;
            // Search messiers
            case "Messier":
                aos = aos.stream().filter(s -> s instanceof Messier).collect(Collectors.toList());
                for (Condition condition : query.getConditions()) {
                    switch (condition.getProperty()){
                        case "ra": case "decl": case "magn": case "distance":
                            aos = genCondition(aos,condition);
                            break;
                        case "number":
                            switch (condition.getOperator()){
                                case "=":
                                    aos = aos.stream()
                                        .filter(s -> ((Messier) s).getNumber().equals(condition.getValue())).collect(
                                            Collectors.toList());
                                    break;
                                case "!=":
                                    aos = aos.stream().filter(s -> (!((Messier) s).getNumber().equals(condition.getValue()))).collect(
                                        Collectors.toList());
                                    break;
                            }
                            break;
                        case "description":
                            switch (condition.getOperator()){
                                case "=":
                                    aos = aos.stream()
                                        .filter(s -> ((Messier) s).getDescription().replaceAll("\\s*", "").toLowerCase().equals(condition.getValue().replaceAll("\\s*", "").toLowerCase())).collect(
                                            Collectors.toList());
                                    break;
                                case "!=":
                                    aos = aos.stream()
                                        .filter(s -> (!((Messier) s).getDescription().equals(condition.getValue()))).collect(
                                            Collectors.toList());
                                    break;
                            }
                            break;
                    }
                }
                break;
            // Search planets
            case "Planet":
                aos = aos.stream().filter(s -> s instanceof Planet).collect(Collectors.toList());
                for (Condition condition : query.getConditions()) {
                    switch (condition.getProperty()){
                        case "ra": case "decl": case "magn": case "distance":
                            aos = genCondition(aos,condition);
                            break;
                        case "name":
                            switch (condition.getOperator()){
                                case "=":
                                    aos = aos.stream()
                                        .filter(s -> ((Planet) s).getName().equals(condition.getValue())).collect(
                                            Collectors.toList());
                                    break;
                                case "!=":
                                    aos = aos.stream()
                                        .filter(s -> (!((Planet) s).getName().equals(condition.getValue()))).collect(
                                            Collectors.toList());
                                    break;
                            }
                            break;
                        case "albedo":
                            switch (condition.getOperator()){
                                case ">":
                                    aos = aos.stream()
                                        .filter(s -> ((Planet) s).getAlbedo() > Double.parseDouble(condition.getValue())).collect(
                                            Collectors.toList());
                                    break;
                                case ">=":
                                    aos = aos.stream()
                                        .filter(s -> ((Planet) s).getAlbedo() >= Double.parseDouble(condition.getValue())).collect(
                                            Collectors.toList());
                                    break;
                                case "=":
                                    aos = aos.stream()
                                        .filter(s -> ((Planet) s).getAlbedo() == Double.parseDouble(condition.getValue())).collect(
                                            Collectors.toList());
                                    break;
                                case "!=":
                                    aos = aos.stream()
                                        .filter(s -> ((Planet) s).getAlbedo() != Double.parseDouble(condition.getValue())).collect(
                                            Collectors.toList());
                                    break;
                                case "<=":
                                    aos = aos.stream()
                                        .filter(s -> ((Planet) s).getAlbedo() <= Double.parseDouble(condition.getValue())).collect(
                                            Collectors.toList());
                                    break;
                                case "<":
                                    aos = aos.stream()
                                        .filter(s -> ((Planet) s).getAlbedo() < Double.parseDouble(condition.getValue())).collect(
                                            Collectors.toList());
                                    break;
                            }
                            break;
                    }
                }
                break;
        }
        return processAllObjects(aos);
    }

    /**
     * Filter collections via general variables(RA, Declination, Magnitude, Distance from the Earth).
     * @param aos The collection needing to be filtered
     * @param condition The condition used to filtered
     * @return The collection after filtered
     */
    private static List<AstronomicalObject> genCondition(List<AstronomicalObject> aos, Condition condition) {
        switch (condition.getProperty()){
            case "ra":
                switch (condition.getOperator()){
                    case ">":
                        aos = aos.stream()
                            .filter(s -> s.getRa() > Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case ">=":
                        aos = aos.stream()
                            .filter(s -> s.getRa() >= Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case "=":
                        aos = aos.stream()
                            .filter(s -> s.getRa() == Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case "!=":
                        aos = aos.stream()
                            .filter(s -> s.getRa() != Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case "<=":
                        aos = aos.stream()
                            .filter(s -> s.getRa() <= Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case "<":
                        aos = aos.stream()
                            .filter(s -> s.getRa() < Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                }
                break;
            case "decl":
                switch (condition.getOperator()){
                    case ">":
                        aos = aos.stream()
                            .filter(s -> s.getDecl() > Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case ">=":
                        aos = aos.stream()
                            .filter(s -> s.getDecl() >= Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case "=":
                        aos = aos.stream()
                            .filter(s -> s.getDecl() == Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case "!=":
                        aos = aos.stream()
                            .filter(s -> s.getDecl() != Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case "<=":
                        aos = aos.stream()
                            .filter(s -> s.getDecl() <= Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case "<":
                        aos = aos.stream()
                            .filter(s -> s.getDecl() < Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                }
                break;
            case "magn":
                switch (condition.getOperator()){
                    case ">":
                        aos = aos.stream()
                            .filter(s -> s.getMagn() > Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case ">=":
                        aos = aos.stream()
                            .filter(s -> s.getMagn() >= Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case "=":
                        aos = aos.stream()
                            .filter(s -> s.getMagn() == Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case "!=":
                        aos = aos.stream()
                            .filter(s -> s.getMagn() != Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case "<=":
                        aos = aos.stream()
                            .filter(s -> s.getMagn() <= Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case "<":
                        aos = aos.stream()
                            .filter(s -> s.getMagn() < Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                }
                break;
            case "distance":
                switch (condition.getOperator()){
                    case ">":
                        aos = aos.stream()
                            .filter(s -> s.getDistance() > Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case ">=":
                        aos = aos.stream()
                            .filter(s -> s.getDistance() >= Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case "=":
                        aos = aos.stream()
                            .filter(s -> s.getDistance() == Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case "!=":
                        aos = aos.stream()
                            .filter(s -> s.getDistance() != Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case "<=":
                        aos = aos.stream()
                            .filter(s -> s.getDistance() <= Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                    case "<":
                        aos = aos.stream()
                            .filter(s -> s.getDistance() < Double.parseDouble(condition.getValue())).collect(
                                Collectors.toList());
                        break;
                }
                break;
        }
        return aos;
    }

    public static Map<String,Vector<Vector<String>>> processAllObjects(List<AstronomicalObject> results)
    {
        Map<String,Vector<Vector<String>>> map = new HashMap<>();
        Vector<Vector<String>> vectors;
        Vector<String> vector;
        for (AstronomicalObject allObject : results) {
            if (allObject instanceof Star){
                vector = new Vector<>();
                vector.add(((Star) allObject).getNumber());
                vector.add(String.valueOf(allObject.getRa()));
                vector.add(String.valueOf(allObject.getDecl()));
                vector.add(String.valueOf(allObject.getMagn()));
                vector.add(String.valueOf(allObject.getDistance()));
                vector.add(((Star) allObject).getType());
                vector.add(((Star) allObject).getConstellation());
                if (map.containsKey("Star")){
                    map.get("Star").add(vector);
                }else {
                    vectors = new Vector<>();
                    vectors.add(vector);
                    map.put("Star", vectors);
                }
            }
            if (allObject instanceof Messier){
                vector = new Vector<>();
                vector.add(((Messier) allObject).getNumber());
                vector.add(String.valueOf(allObject.getRa()));
                vector.add(String.valueOf(allObject.getDecl()));
                vector.add(String.valueOf(allObject.getMagn()));
                vector.add(String.valueOf(allObject.getDistance()));
                vector.add(((Messier) allObject).getConstellation());
                vector.add(((Messier) allObject).getDescription());
                if (map.containsKey("Messier")){
                    map.get("Messier").add(vector);
                }else {
                    vectors = new Vector<>();
                    vectors.add(vector);
                    map.put("Messier", vectors);
                }
            }
            if (allObject instanceof Planet){
                vector = new Vector<>();
                vector.add(((Planet) allObject).getName());
                vector.add(String.valueOf(allObject.getRa()));
                vector.add(String.valueOf(allObject.getDecl()));
                vector.add(String.valueOf(allObject.getMagn()));
                vector.add(String.valueOf(allObject.getDistance()));
                vector.add(String.valueOf(((Planet) allObject).getAlbedo()));
                if (map.containsKey("Planet")){
                    map.get("Planet").add(vector);
                }else {
                    vectors = new Vector<>();
                    vectors.add(vector);
                    map.put("Planet", vectors);
                }
            }
        }
        return map;
    }

    public static List<AstronomicalObject> getAllObjects() {
        return allObjects;
    }

    public static void setAllObjects(List<AstronomicalObject> allObjects) {
        AstronomicalObjectDAO.allObjects = allObjects;
    }
}
