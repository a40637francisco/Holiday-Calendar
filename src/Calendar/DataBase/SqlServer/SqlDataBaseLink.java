package Calendar.DataBase.SqlServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;


public class SqlDataBaseLink {


    private final SQLServerDataSource SQLServerDataSource;
    private Connection connection;

   // private static final  String url ="jdbc:sqlserver://localhost;databaseName=Calendar;user=ISEL;password=francisco791"; // change to dataSource!!!

    private final String dataBaseName = "Calendar";
    private final String serverName = "FRANCISCO-PC";
    private final String userName = "LS";
    private final String userPassword = "quicas";


    public SqlDataBaseLink() throws SQLException {
        SQLServerDataSource = new SQLServerDataSource();
        prepareDataBaseConnection();
    }

    public SQLServerDataSource getSQLServerDataSource(){
        return SQLServerDataSource;
    }

    public Connection getConnection() throws SQLServerException {
        if(connection == null)
            connection = SQLServerDataSource.getConnection();
        return connection;
    }

    public void closeConnection() throws SQLException {
        if(connection != null){
            connection.close();
            connection = null;
        }
    }

    public void prepareDataBaseConnection() {
        SQLServerDataSource.setServerName(serverName);
        SQLServerDataSource.setDatabaseName(dataBaseName);
        SQLServerDataSource.setUser(userName);
        SQLServerDataSource.setPassword(userPassword);
    }





}

