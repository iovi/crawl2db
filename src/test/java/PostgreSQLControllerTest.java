import db.PostgreSQLController;
import org.junit.*;
import pages.Page;
import pages.PageField;

import java.util.*;

public class PostgreSQLControllerTest {
    PostgreSQLController controller;

    @Before
    public void connectsToDB(){
        try {
            controller=new PostgreSQLController();
        }catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.fail();
        }
    }
    @Test
    public void fillsTable(){
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
