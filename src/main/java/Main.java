import db.PostgreSQLController;
import pages.BrowserWorker;


public class Main {
    public static void main(String[] args) {
        PostgreSQLController dbController = new PostgreSQLController();
        BrowserWorker browserWorker = new BrowserWorker();
        Crawler crawler = new Crawler(browserWorker, dbController);
        crawler.crawl();
        browserWorker.closeBrowser();
    }
}
