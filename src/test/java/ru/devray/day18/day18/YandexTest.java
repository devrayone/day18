package ru.devray.day18.day18;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class YandexTest {

    static WebDriver driver;
    static WebDriverWait wait;

    @BeforeAll
    static void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        System.setProperty("webdriver.chrome.driver", "bin\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 5, 50);
        //implicit wait
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //explicit wait
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

        //3. Ввести в поле поиска запрос "porsche 356B 1:18 model"
        WebElement searchField = driver.findElement(By.xpath("//input[@id='text']"));
        searchField.sendKeys("porsche 356B 1:18 model");

        //4. Нажать кнопку найти
        WebElement searchButton = driver.findElement(By.xpath("//div[@class='search2__button']/button"));
        searchButton.click();

        //5. Проверить, что по данному поисковому запросу получено как минимум два результата

        List<WebElement> searchResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@id='search-result']/li")));

        //List<WebElement> searchResults = driver.findElements(By.xpath("//ul[@id='search-result']/li"));
        Assertions.assertTrue(searchResults.size() >= 2 );

        //6. Проверить, что по данному поисковому запросу есть как минимум 3 страницы результатов
        WebElement pageNumberThree = driver.findElement(By.xpath("//div[@class='pager__items']/a[text()='3']"));
        Assertions.assertNotNull(pageNumberThree);

        //7. Перейти на 3-ю страницу результатов
        pageNumberThree.click();

//        8. Проверить, что мы все еще находимся в поисковике Яндекс (URL)

        Assertions.assertTrue(driver.getCurrentUrl().startsWith("https://yandex.ru"));

//        9. Открыть 2-й поисковый результат в выдаче на данной странице

        WebElement secondSearchResult = driver.findElement(By.xpath("//ul[@id='search-result']/li[2]//h2"));
        secondSearchResult.click();

//        10. Убедиться что произошел переход со страницы поисковика на целевую страницу

        Set<String> handles = driver.getWindowHandles();
        handles.remove(driver.getWindowHandle());
        driver.switchTo().window((String)handles.toArray()[0]);

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
        driver.close();
    }
}
