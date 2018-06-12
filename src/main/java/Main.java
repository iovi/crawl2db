import db.PostgreSQLController;
import pages.BrowserWorker;

/**Класс, с которого начинается выполнение приложения*/
public class Main {

    public static void main(String[] args) {
        PostgreSQLController dbController = new PostgreSQLController();
        BrowserWorker browserWorker = new BrowserWorker();

        Crawler crawler = new Crawler(browserWorker, dbController);
        crawler.crawl();

        browserWorker.closeBrowser();
    }
}
