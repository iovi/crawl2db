package db;


import pages.Page;
import pages.PageField;
import java.util.List;

/**
 * Интерфейс дял работы с БД. Корректный порядок работы: </br>
 * <ol>
 * <li>{@link #startWorkingWithDB()}</li>
 * <li>{@link #fillTable(String, List, List)} - один или несколько</li>
 * <li>{@link #endWorkingWithDB()}</li>
 * </ol>
 */
public interface DBController {
    /**Начало работы с базой, необходимо выполнять перед последующими действиями*/
    void startWorkingWithDB();

    /**
     * Заполнение указанной таблицы данными с переданных страниц
     * @param tableName имя таблицы
     * @param pageFields список возможных полей
     * @param pages список страниц с заполненными полями
     */
    void fillTable(String tableName, List<PageField> pageFields, List<Page> pages);

    /**Завершение работы с базой, необходимо выполнять после всех действий*/
    void endWorkingWithDB();
}
