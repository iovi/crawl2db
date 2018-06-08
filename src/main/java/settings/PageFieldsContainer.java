package settings;

import pages.PageField;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

    @XmlRootElement (name="PageFields")
    public class PageFieldsContainer {
        List<PageField> pageFields;
        String pageName;

        @XmlElement (name="Field")
        public List<PageField> getPageFields(){return this.pageFields;}

        public void setPageFields(List<PageField> pageFields){this.pageFields=pageFields;}

        @XmlElement (name="PageName")
        public String getPageName(){return this.pageName;}
        public void setPageName(String pageName){this.pageName=pageName;}
}
