package db;

import java.sql.*;
import java.util.*;

import pages.PageField;
import settings.SettingsExtractor;


public class DBController {
    Connection connection;


    public DBController() throws Exception{

        loadDriver();
    }

    public void createDataStructure(String TableName, Set<PageField> fields){

    }
    public void fillDatabase(Map<String,String> fieldsValues){}

    private void loadDriver() throws Exception {
       Class.forName("org.postgresql.Driver").newInstance();
       DBConfiguration dbConfiguration= SettingsExtractor.extractDBConfiguration();
       connection= DriverManager.getConnection(
               dbConfiguration.getConnectionUrl(),
               dbConfiguration.getUser(),
               dbConfiguration.getPassword());

    }
    public void endWorkingWithDB() {
        try {
            connection.close();
        } catch (SQLException se) { }
    }

}
