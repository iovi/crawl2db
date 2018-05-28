package pages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class PageField {

    String name;
    String xpath;
    String datatype;
    /*public PageField(String name, String xpath,String datatype){
        this.name=name;
        this.xpath=xpath;
        this.datatype=datatype;
    }*/
    public PageField(){}

    public String getXpath(){return this.xpath;}
    public String getName(){return this.name;}
    public String getDatatype(){return this.datatype;}
    public void setXpath(String xpath){this.xpath=xpath;}
    public void setName(String name){this.name=name;}
    public void setDatatype(String datatype){this.datatype=datatype;}


}
