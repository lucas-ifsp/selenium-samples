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
import org.openqa.selenium.support.locators.RelativeLocator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class SeleniumLocatorsTest {
    public static final String PAGE_FILE = "file:///Users/lucas/Git/ensino/tc1/basic-registration-form/authenticate.html";
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
    @DisplayName("Should get elements using various strategies")
    void shouldGetElementsUsingVariousStrategies() {
        driver.get(PAGE_FILE); // path to local page file
        assertThat(driver.findElement(By.id("email")).getText()).isEmpty();// 1
        assertThat(driver.findElement(By.className("registerbtn")).getText()).isEqualTo("Entrar");//2
        assertThat(driver.findElement(By.name("psw")).getText()).isEmpty(); //3
        assertThat(driver.findElement(By.tagName("H1")).getText()).isEqualTo("Login");//4
        assertThat(driver.findElement(By.linkText("Crie uma conta")).getText()).isEqualTo("Crie uma conta");//5
        assertThat(driver.findElement(By.partialLinkText("conta")).getText()).isEqualTo("Crie uma conta");//6
    }

    @Test
    @DisplayName("Should get elements in subset of DOM")
    void shouldGetElementsInSubsetOfDom() throws InterruptedException {
        driver.get(PAGE_FILE);
        driver.findElement(By.id("email")).sendKeys("tc1@ifsp.edu.br");
        driver.findElement(By.id("psw")).sendKeys("123456");
        driver.findElement(By.className("registerbtn")).click();
        final var container = driver.findElement(By.className("container"));
        final var errorDiv = container.findElement(By.tagName("DIV")); // gets element from a subset of the DOM
        System.out.println(errorDiv.getAriaRole());
        assertThat(errorDiv.getText()).isEqualTo("Usuário e/ou senha inválidos");
    }

    @Test
    @DisplayName("Should find element by css selector")
    void shouldFindElementByCssSelector() {
        driver.get(PAGE_FILE);
        driver.findElement(By.id("email")).sendKeys("tc1@ifsp.edu.br");
        driver.findElement(By.id("psw")).sendKeys("123456");
        driver.findElement(By.className("registerbtn")).click();
        final var errorDiv = driver.findElement(By.cssSelector(".container div")); // gets element from a subset of the DOM
        assertThat(errorDiv.getText()).isEqualTo("Usuário e/ou senha inválidos");
    }

    @Test
    @DisplayName("Should find element by xPath")
    void shouldFindElementByXPath() {
        driver.get(PAGE_FILE);
        driver.findElement(By.className("registerbtn")).click();
        final var passwordField = driver.findElement(By.xpath("//input[@type='password']"));
        assertThat(passwordField.getText()).isEmpty();
    }

    @Test
    @DisplayName("Should find all input fields")
    void shouldFindAllInputFields() {
        driver.get(PAGE_FILE);
        final List<WebElement> inputElements = driver.findElements(By.tagName("input"));
        assertThat(inputElements).allMatch(element -> element.getText().isEmpty());
    }

    @Test
    @DisplayName("Should find elements by relative criteria")
    void shouldFindElementsByRelativeCriteria() {
        driver.get(PAGE_FILE);
        final By locator = RelativeLocator.with(By.tagName("a")) // locate something with tag <a>
                .below(By.className("registerbtn")) // that is bellow the button "Entrar"
                .toRightOf(By.id("psw-err"));// and is to the right of the password error message
        final WebElement link = driver.findElement(locator);
        assertThat(link.getText()).isEqualTo("Crie uma conta");
    }
}
