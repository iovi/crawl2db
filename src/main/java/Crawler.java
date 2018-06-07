import db.DBController;
import pages.*;
import settings.SettingsExtractor;


import java.util.*;

public class Crawler {

    private Set<String> pageUrls = new HashSet<String>();
    private List<Page> pages;
    List<PageField> pageFields;
    String startingUrl;
    String nextPageXPath;
    String targetPageXpath;
    String pageName;
    PageWorker pageWorker;


    public Crawler(PageWorker pageWorker){
        this.pageWorker=pageWorker;
        SiteConfiguration siteConfiguration=SettingsExtractor.extractConfiguration(SiteConfiguration.class);
        this.targetPageXpath=siteConfiguration.getTargetPageXPath();
        this.startingUrl=siteConfiguration.getSiteStartingUrl();
        this.pageName=siteConfiguration.getPageName();
        this.nextPageXPath=siteConfiguration.getNextPageXPath();

        PageFieldsContainer pageFieldsContainer=SettingsExtractor.extractConfiguration(PageFieldsContainer.class);
        this.pageFields=pageFieldsContainer.getPageFields();

        pages =new LinkedList<>();
    }



    public void crawl(){
        String url=startingUrl;
        for (int i=0;url!=null && i<3; i++){
            crawlPage(url);
            url=pageWorker.getNextPage(nextPageXPath);
        }
        parsePages();
        try{
            storePages();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }

    }
    private void crawlPage(String url){
        try {
            pageUrls.addAll(pageWorker.getLinksSetByXPath(url,targetPageXpath));
            for (String link : pageUrls){
                System.out.println("page link "+link);
            }

        } catch (Exception e){System.out.print(e.getMessage());}


    }
    private void parsePages(){
        List<PageField> fieldList = SettingsExtractor.extractConfiguration(PageFieldsContainer.class).getPageFields();
        System.out.println(fieldList.toString());

        for (String url: pageUrls){
            System.out.println(url);
            Page page =new Page(url);
            for(PageField field:fieldList){
                String fieldValue=null;
                try {
                    fieldValue=pageWorker.getElementTextByXpath(url,field.getXpath());
                } catch (Exception e) {}
                finally {
                    page.storeField(field.getName(), fieldValue);
                }
            }
            pages.add(page);
        }
    }
    private void storePages()  throws Exception {
        DBController dbController =new DBController(this.pageName);
        dbController.fillTable(pageFields,pages);
    }

}
