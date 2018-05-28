package settings;

import db.DBConfiguration;
import pages.PageField;
import pages.PageFieldsContainer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;


public class SettingsExtractor {




    public static List<PageField> extractPageFieldsList(){
        try {

            File file = new File("fields_zoo.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(PageFieldsContainer.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            PageFieldsContainer container = (PageFieldsContainer) jaxbUnmarshaller.unmarshal(file);
            return container.getPageFields();
        } catch (JAXBException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    public static DBConfiguration extractDBConfiguration(){
        try {

            File file = new File("config/DB_config.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(DBConfiguration.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            DBConfiguration dbConfiguration = (DBConfiguration) jaxbUnmarshaller.unmarshal(file);
            return dbConfiguration;
        } catch (JAXBException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
