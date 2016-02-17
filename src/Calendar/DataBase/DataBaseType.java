package Calendar.DataBase;


import java.util.ArrayList;
import java.util.HashMap;

public abstract class DataBaseType {
    public static HashMap<Queries,DbCommands> dbMethods;

    public interface DbCommands {
        String[][] execute(ArrayList<String> parameters);
    }

    public void addQueries() {
        dbMethods = new HashMap<>();
        dbMethods.put(Queries.AddYear, this::addYear);
        dbMethods.put(Queries.GetYears, this::getYears);
        dbMethods.put(Queries.DeleteYear, this::deleteYear);
        dbMethods.put(Queries.GetAllUsers, this::getAllUsers);
        dbMethods.put(Queries.GetSomeUsers, this::getSomeUsers);
        dbMethods.put(Queries.GetUsersFromDate, this::getUsersFromDate);
    }

    public abstract String[][] callQuery(Queries query, ArrayList<String> parameters);

    public abstract String[][] addYear(ArrayList<String> parameters);
    public abstract String[][] getYears(ArrayList<String> parameters);
    public abstract String[][] deleteYear(ArrayList<String> parameters);
    public abstract String[][] getAllUsers(ArrayList<String> parameters);
    public abstract String[][] getSomeUsers(ArrayList<String> parameters);
    public abstract String[][] getUsersFromDate(ArrayList<String> parameters);




}
