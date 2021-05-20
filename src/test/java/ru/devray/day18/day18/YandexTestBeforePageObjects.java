package ru.devray.day18.day18;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.devray.day18.day18.util.WebDriverWrapper;

import java.util.List;

public class YandexTestBeforePageObjects {

    static WebDriverWrapper driver;

    @BeforeAll
    static void setUp(){
        driver = WebDriverWrapper.getInstance();
    }

    @Test
    @DisplayName("Проверка работоспособности поиска и базовой навигации по поисковым результатам.")
    void testYandexSearch(){

        //1. Открыть страницу yandex.ru
        driver.get("https://yandex.ru");

        //2. Проверить, отображаются ли над поисковой строкой кнопки "Маркет", "Видео", "Картинки",
        // "Карты" (проверяется наличие элементов логотип + текст).
        String[][] navMenuItems = {
                {"market", "Маркет"},
                {"video", "Видео"},
                {"images", "Картинки"},
                {"maps", "Карты"}
        };


        for(String[] menuItem : navMenuItems){
            validateNavMenuSection(menuItem[0], menuItem[1]);
        }

        //3. В"porsche 356вести в поле поиска запрос B 1:18 model"
        WebElement searchField = driver.findElement(By.xpath("//input[@id='text']"));
        searchField.sendKeys("porsche 356B 1:18 model");

        //4. Нажать кнопку найти
        WebElement searchButton = driver.findElement(By.xpath("//div[@class='search2__button']/button"));
        searchButton.click();


        List<WebElement> searchResults = driver.findElements(By.xpath("//ul[@id='search-result']/li"));

        //List<WebElement> searchResults = driver.findElements(By.xpath("//ul[@id='search-result']/li"));
        Assertions.assertTrue(searchResults.size() >= 2 );

        //6. Проверить, что по данному поисковому запросу есть как минимум 3 страницы результатов
        WebElement pageNumberThree = driver.findElement(By.xpath("//div[@class='pager__items']/a[text()='3']"));
        Assertions.assertNotNull(pageNumberThree);

        //7. Перейти на 3-ю страницу результатов
        pageNumberThree.click();

        //8. Проверить, что мы все еще находимся в поисковике Яндекс (URL)

        Assertions.assertTrue(driver.getCurrentUrl().startsWith("https://yandex.ru"));

        //9. Открыть 2-й поисковый результат в выдаче на данной странице

        WebElement secondSearchResult = driver.findElement(By.xpath("//ul[@id='search-result']/li[2]//h2"));
        secondSearchResult.click();

        //10. Убедиться что произошел переход со страницы поисковика на целевую страницу

        //переключиться на новую вкладку
        driver.switchToNewTab();


        Assertions.assertTrue(!driver.getCurrentUrl().startsWith("https://yandex.ru"));
    }

    void validateNavMenuSection(String logo, String logoText){
        WebElement marketLogo = driver.findElement(By.xpath(String.format("//a[@data-id='%s']/div[@class='services-new__icon']", logo)));
        Assertions.assertNotNull(marketLogo);

        WebElement marketLogoText = driver.findElement(By.xpath(String.format("//div[@class='services-new__item-title' and text()='%s']", logoText)));
        Assertions.assertNotNull(marketLogoText);
    }

    @AfterAll
    static void tearDown(){
        driver.quit();
    }
}
