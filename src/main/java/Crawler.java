import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import db.DBController;
import db.PostgreSQLController;
import pages.*;
import settings.PageFieldsContainer;
import settings.SettingsExtractor;
import settings.SiteConfiguration;


import java.util.*;

/**Разборщик сайта, сохраняет данные со страниц и сохраняет их в БД*/
public class Crawler {

    /**Набор URL для посещения*/
    private Set<String> pageUrls = new HashSet<String>();

    /**Список страниц с данными*/
    private List<Page> pages;

    /**Список полей, которые ищутся на страницах*/
    private List<PageField> pageFields;

    /**Стартовый URL (на котором ищутся ссылки на целевые страницы и выполняется переход на следующую)*/
    private String startingUrl;

    /**XPath ссылки на следующую страницу (на которой ищутся целевые страницы)*/
    String nextPageXPath;

    /**XPath ссылки на целевую страницу*/
    String targetPageXpath;

    /**Название целевой странцы*/
    String pageName;

    /**Максимальное количество целевых страниц для обработки*/
    Integer pagesLimit;

    PageWorker pageWorker;
    DBController dbController;

    /***
     * @param pageWorker объект, умеющий выполнять работу с сайтом
     * @param dbController объект, умеющий выполнять работу с БД
     */
    public Crawler(PageWorker pageWorker, DBController dbController){
        this.pageWorker=pageWorker;
        this.dbController=dbController;
        SiteConfiguration siteConfiguration=SettingsExtractor.extractConfiguration(SiteConfiguration.class);
        this.targetPageXpath=siteConfiguration.getTargetPageXPath();
        this.startingUrl=siteConfiguration.getSiteStartingUrl();
        this.pageName=siteConfiguration.getPageName();
        this.nextPageXPath=siteConfiguration.getNextPageXPath();
        this.pagesLimit=siteConfiguration.getPagesLimit();

        PageFieldsContainer pageFieldsContainer=SettingsExtractor.extractConfiguration(PageFieldsContainer.class);
        this.pageFields=pageFieldsContainer.getPageFields();

        pages =new LinkedList<>();
    }


    /**
     * Метод, выполняющий разбор сайта. Алгоритм работы:
     * <ol>
     *     <li>Заходит на стартовую страницу</li>
     *     <li>Ищет и сохраняет ссылки на целевые страницы</li>
     *     <li>Переходит на следующую страницу</li>
     *     <li>Повторяет п.2-3 пока не достигнут лимит на количество целевых страниц или перейти на следующую не удается</li>
     *     <li>Последовательно заходит на все целевые страницы и выполняет их разбор (ищет и сохраняет значения полей)</li>
     *     <li>Сохраняет поля страниц в БД</li>
     * </ol>
     */
    public void crawl(){
        String url=startingUrl;
        while (url!=null){
            Set<String> urls=pageWorker.getLinksSetByXPath(url,targetPageXpath);
            int availableNumOfPages=pagesLimit-pageUrls.size();
            if (urls.size()<availableNumOfPages){
                pageUrls.addAll(urls);
            }else{
                pageUrls.addAll(ImmutableSet.copyOf(Iterables.limit(urls,availableNumOfPages)));
                break;
            }
            url=pageWorker.getNextPage(nextPageXPath);
        }
        System.out.println(pageUrls.size()+" page URLs found");
        parsePages();
        try{
            storePages();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    /**Посещает ссылки на целевые страницы из {@link #pageUrls} и сохраняет данные с этих страниц в {@link #pages}*/
    private void parsePages(){
        List<PageField> fieldList = SettingsExtractor.extractConfiguration(PageFieldsContainer.class).getPageFields();

        for (String url: pageUrls){
            Page page =new Page(url);
            for(PageField field:fieldList){
                String fieldValue=null;
                try {
                    fieldValue=pageWorker.getElementTextByXpath(url,field.getXpath());
                } catch (Exception e) {
                    System.out.println("Error with parsing "+ url+":\n"+e.getMessage());
                }
                finally {
                    page.storeField(field.getName(), fieldValue);
                }
            }
            pages.add(page);
        }
        System.out.println(pages.size()+" pages parsed");
    }

    /**Сохраяет данные со страниц {@link #pages} в БД*/
    private void storePages()  {
        dbController.startWorkingWithDB();
        dbController.fillTable(pageName,pageFields,pages);
        dbController.endWorkingWithDB();
    }

}
