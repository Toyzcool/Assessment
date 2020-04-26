package vo;

import java.util.ArrayList;
import java.util.List;

public class Query {
    // Fields
    private String type;
    private List<Condition> conditions;

    // Constructor
    public Query() {
        this.conditions = new ArrayList<Condition>();
    }

    // Get and Set
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override public String toString() {
        return "Query{" + "type='" + type + '\'' + ", conditions=" + conditions + '}';
    }
}
