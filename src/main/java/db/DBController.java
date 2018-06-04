package db;

import java.sql.*;
import java.util.*;

import pages.Page;
import pages.PageField;
import settings.SettingsExtractor;


public class DBController {
    Connection connection;
    String tableName;


    public DBController(String tableName) throws Exception{
        this.tableName=tableName;
        loadDriver();
    }

    public void createTable (List<PageField> fields) throws SQLException{
        String query="create table if not exists "+tableName +" (" +
                "id serial primary key, " +
                "url varchar unique," +
                "storing_time timestamp";
        for (PageField field : fields){
            query+=", "+field.getName()+" "+field.getDatatype();
        }
        query+=");";
        System.out.println(query);
        Statement statement=connection.createStatement();
        statement.execute(query);
        statement.close();
    }
    public void fillTable(List<PageField> pageFields, List<Page> pages) throws SQLException {
        String insertQueries="";
        for (Page page :pages){
            insertQueries+=insertQuery(pageFields,page);
        }
        if (insertQueries!=null) {
            System.out.println(insertQueries);
            Statement statement=connection.createStatement();
            statement.execute(insertQueries);
            statement.close();
        }
    }

    private String insertQuery(List<PageField> pageFields, Page page){
        String fieldNames="url,storing_time";
        String fieldValues="'"+page.getUrl()+"', current_timestamp";
        String query=null;
        String updateValues="storing_time=current_timestamp";
        String preparedValue;
        if (page.getFields() != null){
            for (Map.Entry<String,String> field:page.getFields().entrySet()){
                System.out.println(field.getValue()+" ");
                preparedValue=prepareValueToDB(pageFields,field.getKey(),field.getValue());
                if (preparedValue!=null){
                    fieldNames+=", "+field.getKey();
                    fieldValues+=", "+preparedValue;
                    updateValues+=", "+field.getKey()+"="+preparedValue;
                }
            }
        }
        if (fieldNames!=null && fieldValues!=null) {
            query = "insert into " + tableName + "(" + fieldNames+") values("+fieldValues+") " +
                    "on conflict (url) do update set "+updateValues+";";
        }
        return query;
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
                    case "timestamp":
                        preparedValue="timestamp'"+ fieldValue + "'";
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
       DBConfiguration dbConfiguration= SettingsExtractor.extractConfiguration(DBConfiguration.class);
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
