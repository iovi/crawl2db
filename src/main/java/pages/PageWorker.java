package pages;

import java.util.Set;

/**Интерфейс для работы с веб-страницами*/
public interface PageWorker {
    /**Ищет и возвращает ссылки на странице по переданному XPath
     * @param url URL на котором происходит поиск
     * @param xpath путь до ссылки в формате XPath
     * @return набор url-ссылок, соответствующих переданному XPath*/
    Set<String> getLinksSetByXPath(String url, String xpath);

    /**Возвращает текст найденного по XPath html-элемента
     *
     * @param url URL на котором происходит поиск
     * @param xpath путь до элемента
     * @return текст элемента
     */
    String getElementTextByXpath(String url, String xpath);

    /**Переходит на следующую страницу
     * @param xpath путь до ссылки на переход
     */
    String getNextPage(String xpath);
}
