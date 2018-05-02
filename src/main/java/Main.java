public class Main {
    public static void main(String[] args) {
        //crawler.collectLinks("https://www.zarplata.ru/vacancy/buhgalteriya-finansy-banki");
        BrowserCrawler browserCrawler=new BrowserCrawler(
                "https://www.zarplata.ru/vacancy/buhgalteriya-finansy-banki",
                "https://",
                "/vacancy/card/");
        browserCrawler.crawl();
        browserCrawler.parseDescriptionPages();
    }
}
