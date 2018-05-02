import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class DescriptionPage {
    List<PageField> fields;
    HashMap<String,String> fieldValues;
    WebDriver driver;
    public DescriptionPage (WebDriver driver){
        this.driver=driver;
        fields =PageFieldsPreparator.preparePageFieldsList2();
    }

    void storeDescription(String url){
        driver.get(url);
        fieldValues=new HashMap<String, String>();
        for(PageField field:fields){
            fieldValues.put(field.getName(), driver.findElement(By.xpath(field.getXpath())).getText());
        }
        System.out.println("map: "+fieldValues.toString());
    }

}
