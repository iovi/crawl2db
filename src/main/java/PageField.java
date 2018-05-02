import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class PageField {

    String name;
    String xpath;
    public PageField(String name, String xpath){
        this.name=name;
        this.xpath=xpath;
    }
    public PageField(){}

    @XmlElement(name="xpath")
    public String getXpath(){return this.xpath;}
    @XmlElement(name="name")
    public String getName(){return this.name;}
    public void setXpath(String xpath){this.xpath=xpath;}
    public void setName(String name){this.name=name;}


}
