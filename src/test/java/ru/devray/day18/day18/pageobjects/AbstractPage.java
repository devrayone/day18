package ru.devray.day18.day18.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.devray.day18.day18.util.WebDriverWrapper;

/**
 * Родительский класс для всех страничных объектов.
 * Описывает общий функционал, присущий каждой из страниц тестируемого веб приложения.
 */
public abstract class AbstractPage {

    /**
     * Адрес данного PageObject
     */
    public String baseUrl;

    Logger log = LogManager.getRootLogger();
    WebDriverWrapper driver = WebDriverWrapper.getInstance();

    public AbstractPage(String baseUrl) {
        this.baseUrl = baseUrl;
        log.trace(String.format("Создал страничный объект для url = '%s'", baseUrl));
    }

    public abstract void open();
    public abstract void checkIsCurrentPage();

    public void close(){
        driver.close();
        log.info(String.format("Закрываю вкладку со страницей '%s'", baseUrl));
    }

    public String getUrl(){
        return driver.getCurrentUrl();
    }

}
