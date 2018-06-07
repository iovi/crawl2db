package pages;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="SiteConfiguration")
public class SiteConfiguration {
    String siteStartingUrl;
    String targetPageXPath;
    String pageName;
    String nextPageXPath;
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
