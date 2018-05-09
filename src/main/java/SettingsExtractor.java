import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.ArrayList;
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

}
