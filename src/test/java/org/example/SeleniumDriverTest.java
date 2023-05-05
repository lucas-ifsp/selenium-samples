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
        WebDriver driver = new ChromeDriver(options); // creates a driver to interact with the browser
        driver.get("https://www.google.com"); // request the page
        Thread.sleep(1000); // waits for 1s.
        driver.quit(); // quits all WebDriver instances and closes the browser
    }

    @Test
    @DisplayName("Should open and close chrome browser using Manager")
    void shouldOpenAndCloseChromeBrowserUsingManager() throws InterruptedException {
        WebDriverManager.safaridriver().setup(); //sets up a valid driver for Chrome.

        // There is a bug in ChromeDriver after Chrome updated to version 111.
        // Add options to driver solve the problem, but it is a temporary workaround
        // For more info: https://groups.google.com/g/chromedriver-users/c/xL5-13_qGaA
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options); ;

        driver.get("https://www.google.com");
        Thread.sleep(1000);
        driver.quit();
    }
}