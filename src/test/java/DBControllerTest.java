import org.junit.Assert;
import org.junit.Test;

public class DBControllerTest {
    @Test
    public void connectsToDB(){
        try {
            DBController controller=new DBController();
            controller.endWorkingWithDB();
        }catch (Exception e) {
            System.err.println(e.getMessage());
            Assert.fail();
        }
    }
}
