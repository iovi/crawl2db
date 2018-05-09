import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.List;

public class Description {
    List<PageField> fields;
    HashMap<String,String> fieldValues;
    public Description(){}{
        fieldValues=new HashMap<String, String>();
    }

    void storeField(String fieldName, String fieldValue){
        fieldValues.put(fieldName,fieldValue);

        /*driver.get(url);
        fieldValues=new HashMap<String, String>();
        for(PageField field:fields){
            fieldValues.put(field.getName(), driver.findElement(By.xpath(field.getXpath())).getText());
        }
        System.out.println("map: "+fieldValues.toString());*/
    }
    HashMap<String,String > getDescriptionFields(){return this.fieldValues;}

}
