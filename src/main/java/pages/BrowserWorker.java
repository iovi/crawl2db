package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**Класс для работы с веб страницами, использующий для этого браузер*/
public class BrowserWorker implements PageWorker {
    WebDriver driver;

    /**Начало работы с браузером. В конце работы рекомендуется выполнить {@link #closeBrowser()}*/
    public BrowserWorker() {
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
    /**Реализация метода для браузера*/
    public Set<String> getLinksSetByXPath(String url, String xpath){
        Set<String> linksSet=new HashSet<>();
        if (!url.equals(driver.getCurrentUrl()))
            driver.get(url);
        List<WebElement> links=driver.findElements(By.xpath(xpath));
        for (WebElement link : links) {
            linksSet.add(link.getAttribute("href"));
        }
        return linksSet;
    }
    /**Реализация метода для браузера*/
    public String getElementTextByXpath(String url, String xpath){
        if (!url.equals(driver.getCurrentUrl()))
            driver.get(url);
        String text=driver.findElement(By.xpath(xpath)).getText();
        return text;
    }
    /**Реализация метода для браузера, выполняется попытка нажать найденную ссылку для перехода*/
    public String getNextPage(String xpath){
        if (xpath ==null) return null;
        try{
                driver.findElement(By.xpath(xpath)).click();
                return driver.getCurrentUrl();
        }  catch (NoSuchElementException e){
            return null;
        }

    }
    /**Закрытие браузера*/
    public void closeBrowser(){
        driver.close();
    }
}
