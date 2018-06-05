package pages;

import java.util.Set;

public interface PageParser {
    Set<String> getLinksSetByXPath(String url, String xpath);
    String getElementTextByXpath(String url, String xpath);
}
