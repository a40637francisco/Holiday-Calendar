package tests;



import Calendar.DataBase.DataBaseType;
import Calendar.DataBase.SqlServer.SqlDataBaseLink;
import Calendar.DataBase.SqlServer.SqlServer;

import java.sql.*;
import java.util.*;
import java.util.Calendar;

import static java.lang.Integer.parseInt;

public class Tests {

    public static final String[] diasSemana ={"","Domingo","Segunda","Terca","Quarta","Quinta","Sexta","Sabado"};


    public static void main(String[] args) {
        try {
            DataBaseType dbLink = new SqlServer();
            dbLink.getYears(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void change(String[] a) {
        a[0] = "b";
    }

    static public void printSQLException(SQLException ex) {
        while (ex != null) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getSQLState());
            System.out.println(ex.getErrorCode());
            // get next exception in object
            ex = ex.getNextException();
        }
    }

    static public void printResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int columnNumber = rsMetaData.getColumnCount();
        String columnName[] = new String[columnNumber];
        for (int i = 0; i < columnNumber; i++) {
            columnName[i] = rsMetaData.getColumnName(i + 1);
        }
        while (rs.next()) {
            for (int i = 0; i < columnNumber; i++) {
                if (i > 0)
                    System.out.print(", ");
                System.out.print(columnName[i] + " " + rs.getObject(i + 1));
            }
            System.out.println();
        }
    }

}
