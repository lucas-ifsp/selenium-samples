package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


class SeleniumDriverTest {

    @Test
    @DisplayName("Should open and close chrome browser")
    void shouldOpenAndCloseChromeBrowser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver"); // sets the driver path
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(); // creates a driver to interact with the browser
        driver.get("https://www.google.com"); // request the page
        Thread.sleep(1000); // waits for 1s.
        driver.quit(); // quits all WebDriver instances and closes the browser
    }

    @Test
    @DisplayName("Should open and close chrome browser using Manager")
    void shouldOpenAndCloseChromeBrowserUsingManager() throws InterruptedException {
        WebDriverManager.chromedriver().setup(); //sets up a valid driver for Chrome.
        // WebDriverManager.operadriver().setup(); // do the same for Opera browser
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        Thread.sleep(1000);
        driver.quit();
    }
}