package db;


import pages.Page;
import pages.PageField;
import java.util.List;

public interface DBController {
    void startWorkingWithDB();
    void fillTable(String tableName, List<PageField> pageFields, List<Page> pages);
    void endWorkingWithDB();
}
