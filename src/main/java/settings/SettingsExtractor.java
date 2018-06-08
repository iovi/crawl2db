package settings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;

/**Класс для извлечения настроек из xml-файлов*/
public class SettingsExtractor {

    static HashMap<Class,String> configFiles=new HashMap<>();
    static{
        configFiles.put(DBConfiguration.class,"config/DB_config.xml");
        configFiles.put(SiteConfiguration.class,"config/site_config.xml");
        configFiles.put(PageFieldsContainer.class,"config/fields.xml");
    }

    /**
     * Возвращает заведенные настройки в виде объекта заданного конфигурационного класса.
     * @param tClass конфигурационный класс, объект которого необходимо вернуть.
     * @return объект, содержащий необходимые настройки.
     * В случае указания "неправильного" класса (объект которого нельзя создать из настроек) -
     * вернется null и выведется ошибка в системном потоке ошибок.
     */
    public static <T> T extractConfiguration(Class<T> tClass){
        try {

            File file = new File(configFiles.get(tClass));
            JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (T) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
