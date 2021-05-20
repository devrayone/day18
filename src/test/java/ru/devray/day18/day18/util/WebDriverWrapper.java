package ru.devray.day18.day18.util;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Здесь используется Паттерн Синглтон
 */
public class WebDriverWrapper {

    private static Logger log = LogManager.getRootLogger();
    private static WebDriverWrapper instance; //единственный экземпляр обертки, которая будет шариться между всеми тестами

    private WebDriver driver;
    private WebDriverWait wait;

    private WebDriverWrapper() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        System.setProperty("webdriver.chrome.driver", "bin\\chromedriver.exe");
        this.driver = new ChromeDriver(options);

        //Альтернативно - вариант для работы с Selenium Grid и подключением к Selenium Grid Hub
        //Сервисы node & hub должны быть запущены!
        //try {
        //    this.driver = new RemoteWebDriver(new URL("http://192.168.2.71:4444/wd/hub"), options);
        //} catch (MalformedURLException e) {
        //    e.printStackTrace();
        //}

        driver.manage().window().maximize();

        this.wait = new WebDriverWait(driver, 5, 200);
    }

    public static WebDriverWrapper getInstance() {
        if (instance == null) {
            instance = new WebDriverWrapper();
            log.debug("Создал экземпляр обертки драйвера.");
        }
        return instance;
    }

    //TODO добавить создание скриншота в случае ошибки по аналогии с findElements
    public WebElement findElement(By locator){
        log.debug(String.format("Ищу элемент по локатору '%s'", locator));

        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor)driver).executeScript("arguments[0]['style']['backgroundColor']='darksalmon';", result);

        log.debug(String.format("Найден элемент по локатору '%s'", locator));
        return result;
    }

    public List<WebElement> findElements(By locator){
        List<WebElement> results = null;

        try {
            log.debug(String.format("Ищу элементы по локатору '%s'", locator));

            results = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

            log.debug(String.format("Найден элементы по локатору '%s'", locator));
        } catch (TimeoutException e) {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            //генерируем имя файла скриншота в виде сегодняшней даты-времени для уникальности имен.
            String newFileName = String.format("%s\\screenshots\\%s.png", System.getProperty("user.dir"),LocalDateTime.now().toString().replace(":", "-"));
            File destination = new File(newFileName);

            try {
                FileUtils.copyFile(source, destination);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            driver.quit();
        }

        return results;
    }

    public void get(String url){
        log.info(String.format("Перехожу на страницу '%s'", url));
        driver.get(url);
        log.info(String.format("Произведен переход на страницу '%s'", url));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void switchToNewTab() {
        log.info("Переключаюсь на новую вкладку.");

        Set<String> handles = driver.getWindowHandles(); //id - tabs and windows - webdriver
        handles.remove(driver.getWindowHandle());
        driver.switchTo().window((String)handles.toArray()[0]);

        log.info(String.format("Переключился на новую вкладку '%s'.", driver.getTitle()));
    }

    public void quit() {
        log.info("Закрываю браузер и все вкладки.");
        driver.quit();
        log.info("Браузер закрыт, вебдрайвер остановлен.");
    }

    public void close() {
        log.debug("Закрываю текущую вкладку.");
        String title = driver.getTitle();
        driver.close();
        log.debug(String.format("Вкладка '%s' закрыта.", title));
    }
}
