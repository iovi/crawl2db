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
                "id serial primary key, " +
                "url varchar";
        for (PageField field : fields){
            sql+=", "+field.getName()+" "+field.getDatatype();
        }
        sql+=");";
        System.out.println(sql);
        Statement statement=connection.createStatement();
        statement.execute(sql);
        statement.close();
    }
    public void fillDatabase(String tableName, List<PageField> pageFields, List<Page> pages) throws SQLException {
        for (Page page :pages){
            String fieldNames="url";
            String fieldValues="'"+page.getUrl()+"'";
            String preparedValue;
            if (page.getFields() != null){
                Iterator<Map.Entry<String,String>> iterator=page.getFields().entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String,String> field = iterator.next();
                    System.out.println(field.getValue()+" ");
                    preparedValue=prepareValueToDB(pageFields,field.getKey(),field.getValue());
                    if (preparedValue!=null){
                        fieldNames+=", "+field.getKey();
                        fieldValues+=", "+preparedValue;
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

    private static String prepareValueToDB(List<PageField> pageFields, String fieldName, String fieldValue){
        String preparedValue=null;
        for(PageField pageField:pageFields) {
            if (pageField.getName().equals(fieldName)) {
                switch (pageField.getDatatype()) {
                    case "varchar":
                        preparedValue= "'" + fieldValue + "'";
                        break;
                    case "date":
                        preparedValue = "date'" + fieldValue + "'";
                        break;
                    case "integer":
                        preparedValue = fieldValue;
                        break;
                }
            }
        }
        return preparedValue;
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
