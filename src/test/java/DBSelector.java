import pages.PageField;
import settings.DBConfiguration;
import settings.SettingsExtractor;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class DBSelector {
    Connection connection;
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
    public HashMap<String,Object> selectAllFromDB(String tableName, String condition, List<PageField> pageFields) throws SQLException{
        String query="select id";
        for(PageField pageField:pageFields){
            query+="," + pageField.getName();
        }
        query+=" from "+tableName+" where "+condition;
        Statement statement=connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        HashMap<String,Object> resultMap=new HashMap<>();
        while(rs.next()){
            for(PageField pageField:pageFields){
                switch (pageField.getDatatype().toLowerCase()) {
                    case "varchar":
                        String varchar= rs.getString(pageField.getName());
                        resultMap.put(pageField.getName(),varchar);
                        break;
                    case "date":
                        String date= rs.getDate(pageField.getName()).toString();
                        resultMap.put(pageField.getName(),date);
                        break;
                    case "timestamp":
                        String time= rs.getTime(pageField.getName()).toString();
                        resultMap.put(pageField.getName(),time);
                        break;
                    case "integer":
                        Integer integer= rs.getInt(pageField.getName());
                        resultMap.put(pageField.getName(),integer);
                        break;
                }

            }
        }
        rs.close();
        return resultMap;
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
