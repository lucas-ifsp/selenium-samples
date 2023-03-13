package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.NoSuchElementException;
import static org.assertj.core.api.Assertions.assertThat;


public class WaitsAndNavigationTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Should result page title start with the text being searched and end with Pesquisa Google")
    void shouldResultPageTitleStartWithTheTextBeingSearchedAndEndWithPesquisaGoogle() throws InterruptedException {
        long start = System.currentTimeMillis();
        driver.get("https://www.google.com");
        driver.findElement(By.className("gLFyf")).sendKeys("Selenium WebDriver");
        Thread.sleep(1000);
        driver.findElement(By.className("gNO89b")).click(); // the search button class is "gNO89b"
        assertThat(driver.getTitle()).startsWith("Selenium WebDriver - Pesquisa Google");
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
    }

    @Test
    @DisplayName("Should result page title start with the text being searched with implicit wait")
    void shouldResultPageTitleStartWithTheTextBeingSearchedWithImplicitWait() {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(200));
        driver.get("https://www.google.com");
        driver.findElement(By.className("gLFyf")).sendKeys("Selenium WebDriver");
        driver.findElement(By.className("gNO89b")).click(); // the search button class is "gNO89b"
        assertThat(driver.getTitle()).startsWith("Selenium WebDriver - Pesquisa Google");
    }

    @Test
    @DisplayName("Should result page title start with the text being searched with explicit wait")
    void shouldResultPageTitleStartWithTheTextBeingSearchedWithExplicitWait() {
        driver.get("https://www.google.com");
        driver.findElement(By.className("gLFyf")).sendKeys("Selenium WebDriver");
        final WebElement button = new WebDriverWait(driver, Duration.ofSeconds(10)) // 10s timeout
                .until(ExpectedConditions.elementToBeClickable(By.className("gNO89b"))); // define expected condition
        button.click();
        assertThat(driver.getTitle()).startsWith("Selenium WebDriver - Pesquisa Google");
    }

    @Test
    @DisplayName("Should result page title start with the text being searched with fluent wait")
    void shouldResultPageTitleStartWithTheTextBeingSearchedWithFluentWait() {
        driver.get("https://www.google.com");
        driver.findElement(By.className("gLFyf")).sendKeys("Selenium WebDriver");
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(700))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.elementToBeClickable(By.className("gNO89b")))
                .click();
        assertThat(driver.getTitle()).startsWith("Selenium WebDriver - Pesquisa Google");
    }

    @Test
    @DisplayName("Should get page info")
    void shouldGetPageInfo() {
        driver.get("https://www.google.com");
        final var softly = new SoftAssertions();
        softly.assertThat(driver.getTitle()).isEqualTo("Google");
        softly.assertThat(driver.getCurrentUrl()).isEqualTo("https://www.google.com/");
        softly.assertThat(driver.getPageSource()).isNotEmpty();
        softly.assertAll();
    }

    @Test
    @DisplayName("Should navigate between pages")
    void shouldNavigateBetweenPages()  {
        driver.get("https://www.google.com");
        driver.navigate().to("https://www.bing.com"); // same as the line above
        final WebElement queryBox = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("sb_form_q")));
        queryBox.sendKeys("Which one is better, Google or Bing?");
        queryBox.sendKeys(Keys.RETURN);
        assertThat(driver.getTitle()).startsWith("Which one is better, Google or Bing?");
        driver.navigate().back(); // return to previous page
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.titleIs("Bing"));
        assertThat(driver.getTitle()).isEqualTo("Bing");
    }

    @Test
    @DisplayName("Should modify window")
    void shouldModifyWindow() throws InterruptedException {
        driver.get("https://www.google.com");
        driver.manage().window().maximize();
        Thread.sleep(1000); //only to see the events. Do not use in real testing code
        driver.manage().window().fullscreen();
        Thread.sleep(1000);
        driver.manage().window().minimize();
        Thread.sleep(1000);
    }

    @Test
    @DisplayName("Should switch between tabs")
    void shouldSwitchBetweenTabs() throws InterruptedException {
        driver.get("https://www.google.com");
        final String originalWindow = driver.getWindowHandle(); //Get the ID of the original window
        Thread.sleep(1000);
        driver.switchTo().newWindow(WindowType.TAB); // Opens a new tab and switches to new tab
        Thread.sleep(1000);
        driver.switchTo().window(originalWindow); //Switch to tab/window using its id
        Thread.sleep(1000);
        driver.getWindowHandles().stream() //Set of all tab ids in the window
                .filter(windowId -> !windowId.equals(originalWindow))
                .findAny()
                .ifPresentOrElse(driver.switchTo()::window, Assertions::fail);
        Thread.sleep(1000);
    }
}
