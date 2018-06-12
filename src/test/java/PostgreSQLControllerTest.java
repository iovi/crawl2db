import db.PostgreSQLController;
import org.junit.*;
import pages.Page;
import pages.PageField;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**Провека класса PostgreSQLController*/
public class PostgreSQLControllerTest {
    PostgreSQLController controller;

    @Before
    public void connectsToDB(){
        try {
            controller=new PostgreSQLController();
            controller.startWorkingWithDB();
        }catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.fail();
        }
    }
    /**Проверяется правильность заполнения таблицы (впоследствии выбирается то же самое что и сохранилось)*/
    @Test
    public void fillTableTest(){
        ArrayList<PageField> fields=new ArrayList<>();
        ArrayList<Page> pages=new ArrayList<Page>();

        fields.add(new PageField("name",null,"varchar"));
        fields.add(new PageField("salary",null,"integer"));
        fields.add(new PageField("birthday",null,"date"));

        Page page1=new Page("http://1.url.com");
        page1.storeField("name","Vasily Terkin");
        page1.storeField("birthday","01.02.1943");
        Page page2=new Page("http://2.url.com");
        page2.storeField("name","Nikolay Gogol");
        page2.storeField("salary","100000");
        page2.storeField("birthday","01.06.1805");
        pages.add(page1);
        pages.add(page2);

        try{
            controller.fillTable("table1",fields,pages);
            DBSelector selector=new DBSelector();
            selector.startWorkingWithDB();
            HashMap<String,Object> selected=selector.selectAllFromDB("table1","url='http://1.url.com'",fields);
            assertEquals((String)selected.get("name"),"Vasily Terkin");

            selected=selector.selectAllFromDB("table1","url='http://2.url.com'",fields);

            assertEquals((String)selected.get("name"),"Nikolay Gogol");
            assertTrue((Integer)selected.get("salary")==100000);

            selector.endWorkingWithDB();
        }catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.fail();
        }
    }
    @After
    public void closesConnection(){
        controller.endWorkingWithDB();
    }


}
