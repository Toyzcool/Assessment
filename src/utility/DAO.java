package utility;

import vo.Query;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public interface DAO {
    Map<String,Vector<Vector<String>>> get(Query query);
}
