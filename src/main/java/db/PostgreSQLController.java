package db;

import java.sql.*;
import java.util.*;

import pages.Page;
import pages.PageField;
import settings.DBConfiguration;
import settings.SettingsExtractor;


/**Класс для взаимодействия с БД */
public class PostgreSQLController implements DBController {
    Connection connection;

    /**
     * Создание таблицы в БД на основе заданного списка полей. Помимо колонок из списка полей создаются олонки id и url
     * */
    private void createTable (String tableName, List<PageField> fields) throws SQLException{
        String query="create table if not exists "+tableName +" (" +
                "id serial primary key, " +
                "url varchar unique," +
                "storing_time timestamp";
        for (PageField field : fields){
            query+=", "+field.getName()+" "+field.getDatatype();
        }
        query+=");";
        Statement statement=connection.createStatement();
        statement.execute(query);
        statement.close();
    }

    /**
     * Заполнение таблицы данными.
     * Старые данные сохраняются, данные вызывающие конфликты по полю url перезаписываются.
     * @param pageFields список полей, определяющих структуру таблицы (используется в {@link #createTable(String, List)} )
     * @param pages страницы с информацией, которая будет сохранена в БД
     * */
    public void fillTable(String tableName,List<PageField> pageFields, List<Page> pages) {
        try{
            createTable(tableName,pageFields);

            String insertQueries="";
            for (Page page :pages){
                insertQueries+=insertQuery(tableName, pageFields,page);
            }
            if (insertQueries!=null) {
                Statement statement=connection.createStatement();
                statement.execute(insertQueries);
                statement.close();
            }
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }

    }
    /**
     * Подготовка запроса вставки данных
     * */
    private String insertQuery(String tableName, List<PageField> pageFields, Page page){
        String fieldNames="url,storing_time";
        String fieldValues="'"+page.getUrl()+"', current_timestamp";
        String query=null;
        String updateValues="storing_time=current_timestamp";
        String preparedValue;
        if (page.getFields() != null){
            for (Map.Entry<String,String> field:page.getFields().entrySet()){
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
    /**
     * Подготовка данных со страницы к "понятному" для БД виду
     * @param pageFields список допустимых полей
     * @param fieldName имя подготавливаемого поля
     * @param fieldValue значение подготавливаемого поля
     * @return подготовленное значение, которое впоследствие можно использовать в SQL-запросах
     * */
    private static String prepareValueToDB(List<PageField> pageFields, String fieldName, String fieldValue){
        String preparedValue=null;
        for(PageField pageField:pageFields) {
            if (pageField.getName().equals(fieldName)) {
                switch (pageField.getDatatype().toLowerCase()) {
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
    /**
     * Подключение b начало работы с БД в соответствии с настройками
     * */
    public void startWorkingWithDB() {
       try{
           Class.forName("org.postgresql.Driver").newInstance();
           DBConfiguration dbConfiguration= SettingsExtractor.extractConfiguration(DBConfiguration.class);
           connection= DriverManager.getConnection(
               dbConfiguration.getConnectionUrl(),
               dbConfiguration.getUser(),
               dbConfiguration.getPassword());
       }catch (Exception e){System.err.println(e.getMessage());}

    }

    /**
     * Завершение работы с базой.
     * После выполнения этой функции остальные функции объекта не смогут быть корректно выполнены
     * */
    public void endWorkingWithDB() {
        try {
            connection.close();
        } catch (SQLException e) { }
    }



}
