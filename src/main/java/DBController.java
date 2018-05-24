import java.sql.*;
import java.util.*;


public class DBController {
    private static final String url = "jdbc:postgresql://localhost:5432/crawler_db1";
    private static final String user = "crawler";
    private static final String password = "crawler1";
    Connection connection;


    public DBController() throws Exception{
        loadDriver();
    }

    public void createDataStructure(String TableName, Set<PageField> fields){

    }
    public void fillDatabase(Map<String,String> fieldsValues){}

    private void loadDriver() throws Exception {
       Class.forName("org.postgresql.Driver").newInstance();
       connection= DriverManager.getConnection(url,user,password);

    }
    public void endWorkingWithDB() {
        try {
            connection.close();
        } catch (SQLException se) { }
    }

}
