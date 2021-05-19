package ru.devray.day18.day18;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ExampleTest {

    WebDriver driver;

    @BeforeEach
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        System.setProperty("webdriver.chrome.driver", "bin/chromedriver.exe");
        driver = new ChromeDriver(options); //launches WebDriver -> launches instance of browser
        driver.manage().window().maximize();
    }

    @Test
    void testSelenium(){
        driver.get("https://www.wikipedia.org");

        WebElement searchField = driver.findElement(By.xpath("//input[@name='search']"));
        searchField.sendKeys("Java");

        WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit']"));
        searchButton.click();

        WebElement articleHeading = driver.findElement(By.xpath("//h1[@id='firstHeading']"));
        Assertions.assertEquals("Java", articleHeading.getText());

    }

    @AfterEach
    void tearDown(){
        //driver.close();
    }

}
