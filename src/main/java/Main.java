public class Main {
    public static void main(String[] args) {
        /*BrowserCrawler browserCrawler=new BrowserCrawler(
                "https://www.zarplata.ru/vacancy/buhgalteriya-finansy-banki",
                "https://",
                "/vacancy/card/");
        */
        BrowserCrawler browserCrawler=new BrowserCrawler(
                "http://www.zoo-zoo.ru/",
                "https://",
                "/messages/");
        browserCrawler.crawl();
        browserCrawler.parseDescriptionPages();
    }
}
