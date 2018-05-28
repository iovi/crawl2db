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

    public void createDataStructure (String tableName, List<PageField> fields) throws SQLException{
        String sql="create table if not exists "+tableName +" (" +
                "id serial primary key";
        for (PageField field : fields){
            sql+=", "+field.getName()+" "+field.getDatatype();
        }
        sql+=");";
        System.out.println(sql);
        Statement statement=connection.createStatement();
        statement.execute(sql);
        statement.close();
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
