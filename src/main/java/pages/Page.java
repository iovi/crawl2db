package pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page {
    HashMap<String,String> fieldValues;
    String url;
    public Page(String url){
        this.url=url;
        fieldValues=new HashMap<String, String>();
    }

    public void storeField(String fieldName, String fieldValue){
        fieldValues.put(fieldName,fieldValue);
    }
    public Map<String,String > getFields(){return this.fieldValues;}
    public String getUrl(){return this.url;}

}
