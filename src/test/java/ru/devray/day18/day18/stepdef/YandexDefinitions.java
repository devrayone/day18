package ru.devray.day18.day18.stepdef;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.devray.day18.day18.pageobjects.MainPage;
import ru.devray.day18.day18.pageobjects.SearchResultsPage;
import ru.devray.day18.day18.pageobjects.TargetPage;
import ru.devray.day18.day18.util.WebDriverWrapper;

public class YandexDefinitions {

    WebDriverWrapper driver;
    MainPage mainPage;
    SearchResultsPage searchResultsPage;
    TargetPage targetPage;

    @Given("use uses ChromeBrowser on Windows")
    public void use_uses_chrome_browser_on_windows() {
        driver = WebDriverWrapper.getInstance();

        mainPage = new MainPage();
        searchResultsPage = new SearchResultsPage();
        targetPage = new TargetPage();
    }

    @When("the user opens yandex main page")
    public void the_user_opens_yandex_main_page() {
        mainPage.open();
    }

    @Then("check is navigation section buttons and logos are present")
    public void check_is_navigation_section_buttons_and_logos_are_present() {
        String[][] navMenuItems = {
                {"market", "Маркет"},
                {"video", "Видео"},
                {"images", "Картинки"},
                {"maps", "Карты"}
        };
        mainPage.checkNavigationMenuItems(navMenuItems);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

}
