package settings;

import pages.PageField;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Конфигурационный класс полей веб-страницы.
 */
@XmlRootElement (name="PageFields")
    public class PageFieldsContainer {
        /**Список возможных полей веб-страницы*/
        List<PageField> pageFields;


        @XmlElement (name="Field")
        public List<PageField> getPageFields(){return this.pageFields;}

        public void setPageFields(List<PageField> pageFields){this.pageFields=pageFields;}

}
