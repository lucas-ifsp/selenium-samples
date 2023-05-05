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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SeleniumInteractionsTest {
    public static final String PAGE_FILE = "file:///Users/lucas/Git/ensino/tc1/basic-registration-form/authenticate.html";
    public static final String FORM_FILE = "file:///Users/lucas/Git/ensino/tc1/basic-registration-form/register.html";
    private WebDriver driver;


    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        // There is a bug in ChromeDriver after Chrome updated to version 111.
        // Add options to driver solve the problem, but it is a temporary workaround
        // For more info: https://groups.google.com/g/chromedriver-users/c/xL5-13_qGaA
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Should test simple interactions")
    void shouldTestSimpleInteractions() throws InterruptedException {
        driver.get(PAGE_FILE);
        driver.findElement(By.id("email")).sendKeys("tc1s5@email.com");
        driver.findElement(By.id("psw")).sendKeys("123123");
        Thread.sleep(1500);
        driver.findElement(By.id("psw")).clear();
        Thread.sleep(1500);
        driver.findElement(By.className("registerbtn")).click();
        Thread.sleep(1500);
        final String pswErrorMessage = driver.findElement(By.id("psw-err")).getText();
        assertThat(pswErrorMessage).isEqualTo("Campo obrigatório");
    }

    @Test
    @DisplayName("Should select elements")
    void shouldSelectElements() throws InterruptedException {
        driver.get(FORM_FILE);
        final WebElement element = driver.findElement(By.id("birth"));
        final Select select = new Select(element); // create a select from a WebElement
        Thread.sleep(1500);
        select.selectByIndex(1);
        Thread.sleep(1500);
        select.selectByValue("2003");
        Thread.sleep(1500);
        select.selectByVisibleText("2001");
        Thread.sleep(1500);
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("2001");
    }
}
