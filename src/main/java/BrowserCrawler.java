import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BrowserCrawler {
    WebDriver driver;
    private Set<String> visitedURLs = new HashSet<String>();
    private Set<String> pagesToVisit = new HashSet<String>();
    private Set<String> descriptionPages = new HashSet<String>();
    String startingUrl;
    String siteMask;
    String descriptionPageMask;

    public BrowserCrawler(String startingUrl, String siteMask, String descriptionPageMask){
        this.descriptionPageMask=descriptionPageMask;
        this.siteMask=siteMask;
        this.startingUrl=startingUrl;
    }



    public void crawl(){
        if (System.getProperty("os.name").startsWith("Windows"))
            System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        else
            System.setProperty("webdriver.gecko.driver", "geckodriver") ;
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        crawlPage(startingUrl);
        driver.close();
    }
    private void collectLinks(String xpath,Set<String> linksSet){
        List<WebElement> links=driver.findElements(By.xpath(xpath));
        for (WebElement link : links) {
            linksSet.add(link.getAttribute("href"));
        }
    }

    private void crawlPage(String url){
        driver.get(url);
        String descriptionPageXpath="/html/body//a[contains(@href,'"+descriptionPageMask+"')]";

        try {
            collectLinks(descriptionPageXpath,descriptionPages);
            for (String link : descriptionPages){
                System.out.println("description link "+link);
            }

        } catch (Exception e){System.out.print(e.getMessage());}

    }
}
