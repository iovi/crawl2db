import db.DBController;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.*;
import settings.SettingsExtractor;


import java.util.*;
import java.util.concurrent.TimeUnit;

public class Crawler {
    //WebDriver driver;
    private Set<String> visitedURLs = new HashSet<String>();
    private Set<String> pagesToVisit = new HashSet<String>();
    private Set<String> pageUrls = new HashSet<String>();
    private List<Page> pages;
    List<PageField> pageFields;
    String startingUrl;
    //String siteMask;
    String targetPageXpath;
    String pageName;
    PageParser parser;


    public Crawler(PageParser parser){
        this.parser=parser;
        SiteConfiguration siteConfiguration=SettingsExtractor.extractConfiguration(SiteConfiguration.class);
        this.targetPageXpath=siteConfiguration.getTargetPageXPath();
        this.startingUrl=siteConfiguration.getSiteStartingUrl();
        this.pageName=siteConfiguration.getPageName();

        PageFieldsContainer pageFieldsContainer=SettingsExtractor.extractConfiguration(PageFieldsContainer.class);
        this.pageFields=pageFieldsContainer.getPageFields();

        pages =new LinkedList<>();
    }



    public void crawl(){
        crawlPage(startingUrl);
        parsePages();
        try{
            storePages();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }

    }
    private void crawlPage(String url){
        try {
            pageUrls=parser.getLinksSetByXPath(url,targetPageXpath);
            for (String link : pageUrls){
                System.out.println("page link "+link);
            }

        } catch (Exception e){System.out.print(e.getMessage());}


    }
    private void parsePages(){
        List<PageField> fieldList = SettingsExtractor.extractConfiguration(PageFieldsContainer.class).getPageFields();
        System.out.println(fieldList.toString());

        for (String url: pageUrls){
            System.out.println(url);
            Page page =new Page(url);
            for(PageField field:fieldList){
                String fieldValue=null;
                try {
                    fieldValue=parser.getElementTextByXpath(url,field.getXpath());
                } catch (Exception e) {}
                finally {
                    page.storeField(field.getName(), fieldValue);
                }
            }
            pages.add(page);
        }
    }
    private void storePages()  throws Exception {
        DBController dbController =new DBController(this.pageName);
        dbController.createTable(pageFields);
        dbController.fillTable(pageFields,pages);
    }

}
