package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumDomTest {
    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Should get the search bar from Google")
    void shouldGetTheSearchBarFromGoogle() {
        driver.get("https://www.google.com");
        final WebElement searchBar = driver.findElement(By.className("gLFyf"));
        final String accessibleName = searchBar.getAccessibleName();
        System.out.println(accessibleName);
    }


    @Test
    @DisplayName("Should open Google and write in the bar")
    void shouldOpenGoogleAndWriteInTheBar() throws InterruptedException {
        driver.get("https://www.google.com");
        final WebElement searchBar = driver.findElement(By.className("gLFyf"));
        searchBar.sendKeys("Selenium WebDriver"); //sends the string content to the DOM object
        Thread.sleep(2000);
    }

    @Test
    @DisplayName("Should open Google and search")
    void shouldOpenGoogleAndSearch() throws InterruptedException {
        driver.get("https://www.google.com");
        Thread.sleep(5000);
        driver.findElement(By.className("gLFyf")).sendKeys("Selenium WebDriver");
        Thread.sleep(5000);
        driver.findElement(By.className("gNO89b")).click(); // the search button class is "gNO89b"
        Thread.sleep(5000);
    }

    @Test
    @DisplayName("Should result page title start with the text being searched and end with Pesquisa Google")
    void shouldResultPageTitleStartWithTheTextBeingSearchedAndEndWithPesquisaGoogle() throws InterruptedException {
        driver.get("https://www.google.com");
        driver.findElement(By.className("gLFyf")).sendKeys("Selenium WebDriver");
        Thread.sleep(200);
        driver.findElement(By.className("gNO89b")).click(); // the search button class is "gNO89b"
        final String title = driver.getTitle();
        Thread.sleep(200);
        assertThat(title).startsWith("Selenium WebDriver - Pesquisa Google");
    }

}
