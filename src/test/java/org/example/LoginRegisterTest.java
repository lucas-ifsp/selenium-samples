package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginRegisterTest {

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
    @DisplayName("Should provide error message if login or password are empty")
    void shouldProvideErrorMessageIfLoginOrPasswordAreEmpty() throws InterruptedException {
        // it will be different from computer to computer =(
        driver.get("file:///Users/lucas/Git/ensino/tc1/basic-registration-form/authenticate.html");
        driver.findElement(By.className("registerbtn")).click();
        Thread.sleep(300);
        final String emailError = driver.findElement(By.id("email-err")).getText();
        final String passwordError = driver.findElement(By.id("psw-err")).getText();
        final var softly = new SoftAssertions();
        softly.assertThat(emailError).isEqualTo("Campo obrigatório");
        softly.assertThat(passwordError).isEqualTo("Campo obrigatório");
        softly.assertAll();
    }

    @Test
    @DisplayName("Should inform that user and or password are invalid if authentication fails")
    void shouldInformThatUserAndOrPasswordAreInvalidIfAuthenticationFails() throws InterruptedException {
        driver.get("file:///Users/lucas/Git/ensino/tc1/basic-registration-form/authenticate.html");
        driver.findElement(By.id("email")).sendKeys("tc1@ifsp.edu.br");
        driver.findElement(By.id("psw")).sendKeys("123456");
        driver.findElement(By.className("registerbtn")).click();
        Thread.sleep(300);
        final String emailError = driver.findElement(By.id("msg-err")).getText();
        assertThat(emailError).isEqualTo("Usuário e/ou senha inválidos");
    }
}
