import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.BrowserWorker;
import pages.Page;
import pages.PageField;
import settings.PageFieldsContainer;
import settings.SettingsExtractor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class BrowserWorkerTest {

    BrowserWorker worker;
    @Before
    public void initializeBrowserParser(){

        worker=new BrowserWorker();
    }
    /**Проверяет разбор страницы - выполняет тестовый разбор выводит резльтаты*/
    @Test
    public void parsePagesTest(){
        List<PageField> fieldList=SettingsExtractor.extractConfiguration(PageFieldsContainer.class).getPageFields();
        Set<String> pageUrls = new HashSet<String>();
        pageUrls.add("https://www.zarplata.ru/vacancy/card/201683400/Buhgalter?position=1&search_query=rubric_id%5B%5D%3D1");
        pageUrls.add("https://www.zarplata.ru/vacancy/card/201694437/Glavniy_buhgalter?position=10&search_query=rubric_id%5B%5D%3D1");
        pageUrls.add("https://www.zarplata.ru/vacancy/card/139651991/Buhgalter_kalkulyator_Lunacharskogo_205?position=13&search_query=rubric_id%5B%5D%3D1");
        pageUrls.add("https://www.zarplata.ru/vacancy/card/201531516/Buhgalter?position=15&search_query=rubric_id%5B%5D%3D1");
        pageUrls.add("https://www.zarplata.ru/vacancy/card/200278600/Kassir_buhgalter?position=19&search_query=rubric_id%5B%5D%3D1");

        for (String url: pageUrls) {
            System.out.println(url);
            Page page = new Page(url);
            for (PageField field : fieldList) {
                String fieldValue = null;
                System.out.println("name="+field.getName());
                System.out.println("xpath="+field.getXpath());
                try {
                    fieldValue = worker.getElementTextByXpath(url, field.getXpath());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    Assert.fail();
                } finally {
                    page.storeField(field.getName(), fieldValue);
                }
            }
            System.out.println(page.getFields().keySet().toString());
        }
    }
    @After
    public void closeBrowser(){
        worker.closeBrowser();
    }
}
