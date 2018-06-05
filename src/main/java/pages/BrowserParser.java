package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.PageParser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BrowserParser implements PageParser {
    WebDriver driver;
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    public BrowserParser() {
        initializeWebDriver();
    }
    private void initializeWebDriver(){
        if (System.getProperty("os.name").startsWith("Windows"))
            System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        else
            System.setProperty("webdriver.gecko.driver", "geckodriver") ;
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    public Set<String> getLinksSetByXPath(String url, String xpath){
        Set<String> linksSet=new HashSet<>();
        driver.get(url);
        List<WebElement> links=driver.findElements(By.xpath(xpath));
        for (WebElement link : links) {
            linksSet.add(link.getAttribute("href"));
        }
        return linksSet;
    }
    public String getElementTextByXpath(String url, String xpath){
        if (!url.equals(driver.getCurrentUrl()))
            driver.get(url);
        String text=driver.findElement(By.xpath(xpath)).getText();
        return text;
    }
    public void closeBrowser(){
        driver.close();
    }
}
