package ru.devray.day18.day18.pageobjects;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MainPage extends AbstractPage{

    public MainPage() {
        super("https://yandex.ru");
        log.debug("Создал PageObject для главной страницу.");
    }

    @Override
    public void open() {
        driver.get(baseUrl);
        log.info("Открыл главную страницу.");
    }

    @Override
    public void checkIsCurrentPage() {
        Assertions.assertTrue(driver.getCurrentUrl().startsWith(baseUrl));
        log.info("Проверил, что нахожусь на корректной странице.");
    }

    public void enterSearchQuery(String query){
        WebElement searchField = driver.findElement(By.xpath("//input[@id='text']"));
        searchField.sendKeys(query);
        log.info(String.format("На главной странице в поле поиска введен поисковый запрос '%s'", query));
    }

    public void clickSearchButton(){
        WebElement searchButton = driver.findElement(By.xpath("//div[@class='search2__button']/button"));
        searchButton.click();
        log.info("Кликнул на кнопку поиска.");
    }

    public void checkNavigationMenuItems(String[][] navMenuItems){
        for(String[] menuItem : navMenuItems){
            checkNavigationMenuItem(menuItem[0], menuItem[1]);
        }
    }

    void checkNavigationMenuItem(String logo, String logoText){
        WebElement marketLogo = driver.findElement(By.xpath(String.format("//a[@data-id='%s']/div[@class='services-new__icon']", logo)));
        Assertions.assertNotNull(marketLogo);

        WebElement marketLogoText = driver.findElement(By.xpath(String.format("//div[@class='services-new__item-title' and text()='%s']", logoText)));
        Assertions.assertNotNull(marketLogoText);

        log.info(String.format("Проверил, что в навигационном меню присутствует логотип и подпись для пункта '%s'.", marketLogoText));
    }
}
