package pages;

import java.util.Set;

public interface PageWorker {
    Set<String> getLinksSetByXPath(String url, String xpath);
    String getElementTextByXpath(String url, String xpath);
    String getNextPage(String xpath);
}
