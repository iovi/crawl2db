package pages;


/**
 * Поле веб-страницы,
 */
public class PageField {

    /**Название поля*/
    String name;
    /**Путь в формате XPath, по которому можно найти данное поле на странице*/
    String xpath;
    /**Тип данных, содержащихся в поле*/
    String datatype;
    public PageField(String name, String xpath,String datatype){
        this.name=name;
        this.xpath=xpath;
        this.datatype=datatype;
    }

    public PageField(){}

    public String getXpath(){return this.xpath;}
    public String getName(){return this.name;}
    public String getDatatype(){return this.datatype;}
    public void setXpath(String xpath){this.xpath=xpath;}
    public void setName(String name){this.name=name;}
    public void setDatatype(String datatype){this.datatype=datatype;}


}
