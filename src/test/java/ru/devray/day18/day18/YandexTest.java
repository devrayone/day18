package ru.devray.day18.day18;

import org.junit.jupiter.api.*;

import ru.devray.day18.day18.pageobjects.MainPage;
import ru.devray.day18.day18.pageobjects.SearchResultsPage;
import ru.devray.day18.day18.pageobjects.TargetPage;
import ru.devray.day18.day18.util.WebDriverWrapper;


public class YandexTest {

    static WebDriverWrapper driver;

    static MainPage mainPage;
    static SearchResultsPage searchResultsPage;
    static TargetPage targetPage;

    @BeforeAll
    static void setUp(){
        driver = WebDriverWrapper.getInstance();

        mainPage = new MainPage();
        searchResultsPage = new SearchResultsPage();
        targetPage = new TargetPage();
    }

    @Test
    @DisplayName("Проверка работоспособности поиска и базовой навигации по поисковым результатам.")
    void testYandexSearch(){

        //Тестовые данные
        String[][] navMenuItems = {
                {"market", "Маркет"},
                {"video", "Видео"},
                {"images", "Картинки"},
                {"maps", "Карты"}
        };
        String searchQuery = "porsche 356B 1:18 model";

        //Тестовая логика
        mainPage.open();
        mainPage.checkNavigationMenuItems(navMenuItems);
        mainPage.enterSearchQuery(searchQuery);
        mainPage.clickSearchButton();

        searchResultsPage.checkPageHasResultItemsCount(2);
        searchResultsPage.checkResultPagesCount(2);
        searchResultsPage.clickResultPageNumber(3);
        searchResultsPage.checkIsCurrentPage();
        searchResultsPage.clickResultItemNumber(2);

        targetPage.checkIsCurrentPage();

    }

    @AfterAll
    static void tearDown(){
        driver.quit();
    }
}
