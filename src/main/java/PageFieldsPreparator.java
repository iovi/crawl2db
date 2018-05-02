import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class PageFieldsPreparator {



    public static List<PageField> preparePageFieldsList(){
        List<PageField> fields=new ArrayList<PageField>();
        fields.add(new PageField("VacancyName", "//h1"));
        fields.add(new PageField("Salary", "//strong[starts-with(@class,'salary')]"));
        fields.add(new PageField("Company", "//a[starts-with(@href,'/companies/')]"));
        return fields;
    }
    public static List<PageField> preparePageFieldsList2(){
        try {

            File file = new File("fields.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(PageFieldsContainer.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            PageFieldsContainer container = (PageFieldsContainer) jaxbUnmarshaller.unmarshal(file);
            for (PageField pageField: container.getPageFields()){
                System.out.println(pageField.getName() + " = " +pageField.getXpath());
            }
            return container.getPageFields();
        } catch (JAXBException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
