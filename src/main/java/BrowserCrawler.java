import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.Page;
import pages.PageField;
import settings.SettingsExtractor;


import java.util.*;
import java.util.concurrent.TimeUnit;

public class BrowserCrawler {
    WebDriver driver;
    private Set<String> visitedURLs = new HashSet<String>();
    private Set<String> pagesToVisit = new HashSet<String>();
    private Set<String> pageUrls = new HashSet<String>();
    private Set<Page> pages;
    String startingUrl;
    String siteMask;
    String targetPageXpath;

    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    public BrowserCrawler(String startingUrl, String siteMask, String targetPageXpath){
        this.targetPageXpath=targetPageXpath;
        this.siteMask=siteMask;
        this.startingUrl=startingUrl;
        pages =new HashSet<Page>();
    }

    private void initializeWebDriver(){
        if (System.getProperty("os.name").startsWith("Windows"))
            System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        else
            System.setProperty("webdriver.gecko.driver", "geckodriver") ;
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void crawl(){
        initializeWebDriver();
        crawlPage(startingUrl);
        parsePages();
        //driver.close();

    }
    private void collectLinks(String xpath,Set<String> linksSet){
        List<WebElement> links=driver.findElements(By.xpath(xpath));
        for (WebElement link : links) {
            linksSet.add(link.getAttribute("href"));
        }
    }

    private void crawlPage(String url){
        driver.get(url);

        try {
            collectLinks(targetPageXpath, pageUrls);
            for (String link : pageUrls){
                System.out.println("page link "+link);
            }

        } catch (Exception e){System.out.print(e.getMessage());}

    }
    private void parsePages(){
        List<PageField> fieldList = SettingsExtractor.extractPageFieldsList();
        System.out.println(fieldList.toString());

        for (String url: pageUrls){
            System.out.println(url);
            driver.get(url);
            Page page =new Page(url);
            for(PageField field:fieldList){
                String fieldValue=null;
                try {
                    fieldValue=driver.findElement(By.xpath(field.getXpath())).getText();
                } catch (Exception e) {}
                finally {
                    page.storeField(field.getName(), fieldValue);
                }
            }
            pages.add(page);
        }
    }

}
