package db;

import java.sql.*;
import java.util.*;

import pages.Page;
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
    public void fillDatabase(String tableName, List<Page> pages) throws SQLException {
        System.out.println("start filling ");
        for (Page page :pages){
            String fieldNames="";
            String fieldValues="";
            if (page.getFields() != null){
                Iterator<Map.Entry<String,String>> iterator=page.getFields().entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String,String> field = iterator.next();
                    System.out.println(field.getKey()+" ");
                    fieldNames+=field.getKey();
                    fieldValues+=field.getValue();
                    if (iterator.hasNext()){
                        fieldNames+=",";
                        fieldValues+=",";
                    }
                }
            }
            if (fieldNames!=null && fieldValues!=null) {
                String sql = "insert into " + tableName + "(" + fieldNames+") values("+fieldValues+");";
                System.out.println(sql);
                Statement statement=connection.createStatement();
                statement.execute(sql);
                statement.close();
            }
        }
    }

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
