import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import db.DBController;
import db.PostgreSQLController;
import pages.*;
import settings.PageFieldsContainer;
import settings.SettingsExtractor;
import settings.SiteConfiguration;


import java.util.*;

public class Crawler {

    private Set<String> pageUrls = new HashSet<String>();
    private List<Page> pages;
    List<PageField> pageFields;
    String startingUrl;
    String nextPageXPath;
    String targetPageXpath;
    String pageName;
    Integer pagesLimit;

    PageWorker pageWorker;
    DBController dbController;


    public Crawler(PageWorker pageWorker, PostgreSQLController dbController){
        this.pageWorker=pageWorker;
        this.dbController=dbController;
        SiteConfiguration siteConfiguration=SettingsExtractor.extractConfiguration(SiteConfiguration.class);
        this.targetPageXpath=siteConfiguration.getTargetPageXPath();
        this.startingUrl=siteConfiguration.getSiteStartingUrl();
        this.pageName=siteConfiguration.getPageName();
        this.nextPageXPath=siteConfiguration.getNextPageXPath();
        this.pagesLimit=siteConfiguration.getPagesLimit();

        PageFieldsContainer pageFieldsContainer=SettingsExtractor.extractConfiguration(PageFieldsContainer.class);
        this.pageFields=pageFieldsContainer.getPageFields();

        pages =new LinkedList<>();
    }



    public void crawl(){
        String url=startingUrl;
        while (url!=null){
            Set<String> urls=pageWorker.getLinksSetByXPath(url,targetPageXpath);
            int availableNumOfPages=pagesLimit-pageUrls.size();
            if (urls.size()<availableNumOfPages){
                pageUrls.addAll(urls);
            }else{
                pageUrls.addAll(ImmutableSet.copyOf(Iterables.limit(urls,availableNumOfPages)));
                break;
            }
            url=pageWorker.getNextPage(nextPageXPath);
        }
        System.out.println(pageUrls.size()+" page URLs found");
        parsePages();
        try{
            storePages();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }

    }
    private void parsePages(){
        List<PageField> fieldList = SettingsExtractor.extractConfiguration(PageFieldsContainer.class).getPageFields();

        for (String url: pageUrls){
            Page page =new Page(url);
            for(PageField field:fieldList){
                String fieldValue=null;
                try {
                    fieldValue=pageWorker.getElementTextByXpath(url,field.getXpath());
                } catch (Exception e) {
                    System.out.println("Error with parsing "+ url+":\n"+e.getMessage());
                }
                finally {
                    page.storeField(field.getName(), fieldValue);
                }
            }
            pages.add(page);
        }
        System.out.println(pages.size()+" pages parsed");
    }
    private void storePages()  {
        dbController.startWorkingWithDB();
        dbController.fillTable(pageName,pageFields,pages);
        dbController.endWorkingWithDB();
    }

}
