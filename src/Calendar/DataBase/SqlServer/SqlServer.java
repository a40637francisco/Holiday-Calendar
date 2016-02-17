package Calendar.DataBase.SqlServer;


import Calendar.CalendarRules.Months;
import Calendar.DataBase.DataBaseType;
import Calendar.DataBase.Queries;

import java.sql.*;
import java.util.ArrayList;

public class SqlServer extends DataBaseType{

    private SqlDataBaseLink dBLink;
    private SqlQueriesBuilder sqlQueriesImplementation;

    public SqlServer() {
        try {
            addQueries();
            dBLink = new SqlDataBaseLink();
            sqlQueriesImplementation = new SqlQueriesBuilder();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet getResultSet(String q) {
        ResultSet rs = null;
        try {
            Connection c = dBLink.getSQLServerDataSource().getConnection();
            Statement stmt = c.createStatement();
            rs = stmt.executeQuery(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    private String[][] resultSetToBiArray(ResultSet rs) {
        String[][] ret = null;
        try {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnNumber = rsMetaData.getColumnCount();
            ret = new String[columnNumber][20];
            int j = 0;
            while (rs.next()) {
                for (int i = 0; i < columnNumber; i++) {
                    if(j >= ret[i].length) resizeArray(ret);
                   ret[i][j] =  rs.getObject(i + 1).toString();
                }
                ++j;
            }



        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                dBLink.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }

    private void resizeArray(String[][] ret) {

    }

    @Override
    public String[][] callQuery(Queries query, ArrayList<String> parameters) {
        String[][] r = dbMethods.get(query).execute(parameters);
        return r;
    }

    @Override
    public String[][] addYear(ArrayList<String> parameters) {
        return new String[0][];
    }

    @Override
    public String[][] getYears(ArrayList<String> parameters) {
        String q = sqlQueriesImplementation.writeQuery("GET/Year");
        ResultSet rs = getResultSet(q);
        return resultSetToBiArray(rs);
    }

    @Override
    public String[][] deleteYear(ArrayList<String> parameters) {
        return new String[0][];
    }

    @Override
    public String[][] getAllUsers(ArrayList<String> parameters) {
        String q = sqlQueriesImplementation.writeQuery("GET/Employee&EmployeeName");
        ResultSet rs = getResultSet(q);
        return resultSetToBiArray(rs);
    }

    @Override
    public String[][] getSomeUsers(ArrayList<String> parameters) {
       return null;
    }

    @Override
    public String[][] getUsersFromDate(ArrayList<String> parameters) {
        String day = parameters.get(0);
        String month = (Months.valueOf(parameters.get(1)).ordinal() + 1) + "";
        String year = parameters.get(2);
        String q = sqlQueriesImplementation.writeQuery("GET/date&DateEmployeeName/DateDay="+day+"&"+"DateMonth="+month+"&"+"DateYear="+year);
        ResultSet rs = getResultSet(q);
        return resultSetToBiArray(rs);
    }


}
