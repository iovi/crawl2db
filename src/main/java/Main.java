public class Main {
    public static void main(String[] args) {
        /*BrowserCrawler browserCrawler=new BrowserCrawler(
                "https://www.zarplata.ru/vacancy/buhgalteriya-finansy-banki",
                "https://",
                "/html/body//a[contains(@href,'/vacancy/card/')]");
*/
        BrowserCrawler browserCrawler=new BrowserCrawler();
        browserCrawler.crawl();
    }
}
