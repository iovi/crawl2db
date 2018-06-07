import pages.BrowserWorker;

public class Main {
    public static void main(String[] args) {
        BrowserWorker browserWorker=new BrowserWorker();
        Crawler crawler =new Crawler(browserWorker);
        crawler.crawl();
        //parser.closeBrowser();
    }
}
