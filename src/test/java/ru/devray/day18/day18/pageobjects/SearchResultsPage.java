package ru.devray.day18.day18.pageobjects;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultsPage extends AbstractPage{

    public SearchResultsPage() {
        super("https://yandex.ru/search");
        log.trace("Создал PageObject для страницы поисковых результатов");
    }

    @Override
    public void open() {
        driver.get(baseUrl);
        log.info("Открыл страницу поисковых результатов.");
    }

    @Override
    public void checkIsCurrentPage() {
        Assertions.assertTrue(driver.getCurrentUrl().startsWith("https://yandex.ru"));
        log.info("Убедились, что находимся на странице поисковых результатов.");
    }


    public void checkPageHasResultItemsCount(int count){
        List<WebElement> searchResults = driver.findElements(By.xpath("//ul[@id='search-result']/li"));
        Assertions.assertTrue(searchResults.size() >= count );
        log.info(String.format("Убедились, что в поисковой выдаче есть как минимум '%d' результатов.", count));
    }

    public WebElement checkResultPagesCount(int count){
        WebElement pageNumber = driver.findElement(By.xpath(String.format("//div[@class='pager__items']/a[text()='%d']", count)));
        Assertions.assertNotNull(pageNumber);
        log.info(String.format("Убедились, что в поисковой выдаче есть как минимум '%d' страниц с результатами.", count));
        return pageNumber;
    }

    public void clickResultPageNumber(int index){
        WebElement pageNumberThree = checkResultPagesCount(3);
        pageNumberThree.click();
        log.info(String.format("Перехожу на страницу результатов #%d", index));
    }

    public void clickResultItemNumber(int number){
        WebElement secondSearchResult = driver.findElement(By.xpath(String.format("//ul[@id='search-result']/li[%d]//h2", number)));
        secondSearchResult.click();
        driver.switchToNewTab();
        log.info(String.format("Выбираю (кликаю) %d-й результат поиска", number));
    }


}
