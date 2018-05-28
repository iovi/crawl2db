import db.DBController;
import org.junit.*;
import pages.PageField;
import settings.SettingsExtractor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DBControllerTest {
    DBController controller;

    @Before
    public void connectsToDB(){
        try {
            controller=new DBController();
        }catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.fail();
        }
    }
    @Test
    public void createsTable(){
        try{
            List<PageField> pageFields= SettingsExtractor.extractPageFieldsList();
            controller.createDataStructure("Table2",pageFields);
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
