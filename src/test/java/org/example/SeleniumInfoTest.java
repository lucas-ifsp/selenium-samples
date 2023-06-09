package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumInfoTest {
    public static final String PAGE_FILE = "file:///Users/lucas/Git/ensino/tc1/basic-registration-form/authenticate.html"; //PUT YOUR URL HERE

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
    @DisplayName("Should assert info from web element")
    void shouldAssertInfoFromWebElement() throws InterruptedException {
        driver.get(PAGE_FILE);
        final var email = driver.findElement(By.id("email"));
        email.sendKeys("student@univerty.com");
        Thread.sleep(1000);
        final SoftAssertions softly = new SoftAssertions();
        softly.assertThat(email.isEnabled()).as("Enabled").isTrue();
        softly.assertThat(email.isDisplayed()).as("Displayed").isTrue();
        softly.assertThat(email.getAttribute("value")).as("E-mail text").isEqualTo("student@univerty.com");
        softly.assertThat(email.getTagName()).as("Tag name").isEqualTo("input");
        softly.assertThat(email.getCssValue("padding")).as("Padding").isEqualTo("15px");
        softly.assertAll();
    }
}
