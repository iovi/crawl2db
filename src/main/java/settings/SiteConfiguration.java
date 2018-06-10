package settings;

import javax.xml.bind.annotation.XmlRootElement;

/**Конфигурация разбираемого сайта*/
@XmlRootElement(name="SiteConfiguration")
public class SiteConfiguration {
    /**URL, с которого начинается разбор*/
    String siteStartingUrl;
    /**XPath ссылки на следующую страницу, на которой будет выполняться разбор*/
    String nextPageXPath;
    /**XPath ссылок на целевые страницы (те, на которых содержится интересующая инфомрмация*/
    String targetPageXPath;
    /**Название целевых страниц*/
    String pageName;
    /**Максимальное количество целевых страниц для обработки*/
    Integer pagesLimit;

    public String getTargetPageXPath() {
        return targetPageXPath;
    }

    public void setTargetPageXPath(String targetPageXPath) {
        this.targetPageXPath = targetPageXPath;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getSiteStartingUrl() {

        return siteStartingUrl;
    }

    public void setSiteStartingUrl(String startingUrl) {
        this.siteStartingUrl = startingUrl;
    }

    public String getNextPageXPath() {
        return nextPageXPath;
    }

    public void setNextPageXPath(String nextPageXPath) {
        this.nextPageXPath = nextPageXPath;
    }

    public Integer getPagesLimit() {
        return pagesLimit;
    }

    public void setPagesLimit(Integer pagesLimit) {
        this.pagesLimit = pagesLimit;
    }
}
