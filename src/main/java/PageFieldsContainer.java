import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

    @XmlRootElement (name="PageFields")
    class PageFieldsContainer {
        List<PageField> pageFields;


        @XmlElement (name="Field")
        public List<PageField> getPageFields(){return this.pageFields;}

        public void setPageFields(List<PageField> pageFields){this.pageFields=pageFields;}

}
