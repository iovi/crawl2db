import pages.BrowserParser;

public class Main {
    public static void main(String[] args) {
        BrowserParser parser=new BrowserParser();
        Crawler crawler =new Crawler(parser);
        crawler.crawl();
        //parser.closeBrowser();
    }
}
