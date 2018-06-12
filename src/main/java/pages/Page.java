package pages;

import java.util.HashMap;
import java.util.Map;

/**Класс веб-страницы*/
public class Page {
    /**Пары "имя"-"значение" найденных на странице полей*/
    HashMap<String,String> fieldValues;
    /**Адрес страницы*/
    String url;
    public Page(String url){
        this.url=url;
        fieldValues=new HashMap<>();
    }

    /**Сохраняет поле страницы
     * @param fieldName имя поля
     * @param fieldValue значение поля
     * */
    public void storeField(String fieldName, String fieldValue){
        fieldValues.put(fieldName,fieldValue);
    }

    public Map<String,String > getFields(){return this.fieldValues;}

    public String getUrl(){return this.url;}

}
